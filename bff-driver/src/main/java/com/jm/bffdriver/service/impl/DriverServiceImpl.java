package com.jm.bffdriver.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.bffdriver.controller.form.*;
import com.jm.bffdriver.feign.DrServiceApi;
import com.jm.bffdriver.feign.OdrServiceApi;
import com.jm.bffdriver.service.DriverService;
import com.jm.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @date 2022/11/28 21:51
 * @version 1.0
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;

    @Resource
    private OdrServiceApi odrServiceApi;

    @Override
    @Transactional
    @LcnTransaction
    public long registerNewDriver(RegisterNewDriverForm form) {
        R r = drServiceApi.RegisterNewDriver(form);
        Long userId = Convert.toLong(r.get("userId"));
        return userId;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int updateDriverAuth(UpdateDriverAuthForm form) {
        R r = drServiceApi.updateDriverAuth(form);
        Integer rows = Convert.toInt(r.get("rows"));
        return rows;
    }

    @Override
    public String createDriverFaceModel(CreateDriverFaceModelForm form) {
        R r = drServiceApi.createDriverFaceModel(form);
        String result = MapUtil.getStr(r, "result");
        return result;
    }

    @Override
    public HashMap login(LoginForm form) {
        R r = drServiceApi.login(form);
        HashMap map = (HashMap)r.get("result");
        return map;
    }

    @Override
    public HashMap searchDriverBaseInfo(SearchDriverBaseInfoForm form) {
        R r = drServiceApi.searchDriverBaseInfo(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }

    @Override
    public HashMap searchWorkBeanData(long driverId) {
        //查询司机当天的业务数据
        SearchDriverTodayBusinessDataForm form_1 = new SearchDriverTodayBusinessDataForm();
        form_1.setDriverId(driverId);
        R r = odrServiceApi.searchDriverTodayBusinessData(form_1);
        HashMap business = (HashMap) r.get("result");

        //查询司机的设置
        SearchDriverSettingsForm form_2 = new SearchDriverSettingsForm();
        form_2.setDriverId(driverId);
        r = drServiceApi.searchDriverSettings(form_2);
        HashMap settings = (HashMap) r.get("result");
        HashMap result = new HashMap(){{
            put("business",business);
            put("settings",settings);
        }};
        return result;
    }
}