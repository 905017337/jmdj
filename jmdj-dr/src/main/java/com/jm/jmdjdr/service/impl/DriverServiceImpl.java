package com.jm.jmdjdr.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONObject;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.common.exception.HxdsException;
import com.jm.common.util.MicroAppUtil;

import com.jm.jmdjdr.mapper.DriverDao;
import com.jm.jmdjdr.mapper.DriverSettingsDao;
import com.jm.jmdjdr.mapper.WalletDao;
import com.jm.jmdjdr.pojo.DriverSettingsEntity;
import com.jm.jmdjdr.pojo.WalletEntity;
import com.jm.jmdjdr.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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