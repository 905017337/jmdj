package com.jm.jmdjdr.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.common.exception.HxdsException;
import com.jm.common.util.MicroAppUtil;

import com.jm.common.util.PageUtils;
import com.jm.jmdjdr.mapper.DriverDao;
import com.jm.jmdjdr.mapper.DriverSettingsDao;
import com.jm.jmdjdr.mapper.WalletDao;
import com.jm.jmdjdr.pojo.DriverSettingsEntity;
import com.jm.jmdjdr.pojo.WalletEntity;
import com.jm.jmdjdr.service.DriverService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.iai.v20180301.IaiClient;
import com.tencentcloudapi.iai.v20180301.models.CreatePersonRequest;
import com.tencentcloudapi.iai.v20180301.models.CreatePersonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @date 2022/11/28 18:25
 * @version 1.0
 */
@Service
@Slf4j
public class DriverServiceImpl implements DriverService {

    @Value("${tencent.cloud.secretId}")
    private String secretId;

    @Value("${tencent.cloud.secretkey}")
    private String secretkey;

    @Value("${tencent.cloud.face.groupName}")
    private String groupName;

    @Value("${tencent.cloud.face.region}")
    private String region;

    @Resource
    private MicroAppUtil microAppUtil;

    @Resource
    private DriverDao driverDao;

    @Resource
    private WalletDao walletDao;

    @Resource
    private DriverSettingsDao settingsDao;


    @Override
    @Transactional
    @LcnTransaction
    public String createDriverFaceModel(long driverId,String photo){
        //查询员工的姓名和性别
        HashMap map = driverDao.searchDriverNameAndSex(driverId);
        String name = MapUtil.getStr(map, "name");
        String sex = MapUtil.getStr(map,"sex");


        //腾讯云端创建司机面部档案
        Credential cred = new Credential(secretId,secretkey);
        IaiClient client = new IaiClient(cred,region);
        try {
            CreatePersonRequest req = new CreatePersonRequest();
            req.setGroupId(groupName); //人员库ID
            req.setPersonId(driverId+""); //人员ID
            long gender = sex.equals("男")?1L:2L;
            req.setGender(gender);
            req.setQualityControl(4L); //照片质量登记
            req.setUniquePersonControl(4L);  //重复人员识别登记
            req.setPersonName(name); //人员名称
            req.setImage(photo); //base64照片
            CreatePersonResponse resp = client.CreatePerson(req);
            if(StrUtil.isNotBlank(resp.getFaceId())){
                //更新司机表的archive字段
                int rows = driverDao.updadteDriverArchive(driverId);
                if(rows != 1){
                    return "更新司机归档字段失败";
                }
            }
        }catch (Exception e){
            log.error("创建腾讯云端司机档案失败",e);
            return "创建腾讯云端司机档案失败";
        }
        return "";
    }

    @Override
    public HashMap login(String code) {
        String openId = microAppUtil.getOpenId(code);
        HashMap result = driverDao.login(openId);
        if(result != null && result.containsKey("archive")){
            int temp = MapUtil.getInt(result,"archive");
            boolean archive = temp == 1?true:false;
            result.replace("archive",archive);
        }
        return result;
    }

    @Override
    public HashMap searchDriverBaseInfo(long driverId) {

        HashMap result = driverDao.searchDriverBaseInfo(driverId);
        JSONObject summary = JSONUtil.parseObj(MapUtil.getStr(result, "summary"));
        result.replace("summary",summary);
        return result;
    }

    @Override
    public PageUtils searchDriverByPage(Map param) {
        long count = driverDao.searchDriverCount(param);
        ArrayList<HashMap> list = null;
        if(count == 0){
            list = new ArrayList<>();
        }else {
            list = driverDao.searchDriverByPage(param);
        }
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }

    @Override
    public HashMap searchDriverAuth(long driverId) {
        HashMap result = driverDao.searchDriverAuth(driverId);
        return result;
    }

    @Override
    public HashMap searchDriverRealSummary(long driverId) {
        HashMap map = driverDao.searchDriverRealSummary(driverId);
        return map;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int updateDriverRealAuth(Map param) {
        int rows = driverDao.updateDriverRealAuth(param);
        return rows;
    }


    @Override
    @Transactional
    @LcnTransaction
    public String registerNewDriver(Map param) {
        String code = MapUtil.getStr(param,"code");
        String openId = microAppUtil.getOpenId(code);

        HashMap tempParam = new HashMap(){{
            put("openId",openId);
        }};
        if(driverDao.hasDriver(tempParam)!= 0){
            throw new HxdsException("该微信无法注册");
        }
        param.put("openId",openId);
        driverDao.registerNewDriver(param);
        String driverId =driverDao.searchDriverId(openId);

        DriverSettingsEntity settingsEntity = new DriverSettingsEntity();
        settingsEntity.setDriverId(Long.parseLong(driverId));

        JSONObject json = new JSONObject();
        json.set("orientation","");
        json.set("listenService",true);
        json.set("orderDistance",0);
        json.set("rangeDistance",5);
        json.set("autoAccept",false);
        settingsEntity.setSettings(json.toString());
        settingsDao.insertDriverSettings(settingsEntity);


        //钱包记录
        WalletEntity wallet = new WalletEntity();
        wallet.setDriverId(Long.parseLong(driverId));
        wallet.setBalance(new BigDecimal("0"));
        wallet.setPassword(null);
        walletDao.insert(wallet);

        return driverId;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int updateDriverAuth(Map<String, Object> param) {

        return driverDao.updateDriverAuth(param);
    }


}