package com.jm.bffdriver.service.impl;

import com.jm.bffdriver.controller.form.RemoveLocationCacheForm;
import com.jm.bffdriver.controller.form.UpdateLocationCacheForm;
import com.jm.bffdriver.controller.form.UpdateOrderLocationCacheForm;
import com.jm.bffdriver.feign.MpsServiceApi;
import com.jm.bffdriver.service.DriverLocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/10 13:11
 */
@Service
public class DriverLocationServiceImpl implements DriverLocationService {

    @Resource
    private MpsServiceApi mpsServiceApi;

    @Override
    public void removeLocationCache(RemoveLocationCacheForm form) {
        mpsServiceApi.removeLocationCache(form);
    }

    @Override
    public void updateLocationCache(UpdateLocationCacheForm form) {
        mpsServiceApi.updateLocationCache(form);
    }

    @Override
    public void updateOrderLocationCache(UpdateOrderLocationCacheForm form) {
        mpsServiceApi.updateOrderLocationCache(form);
    }



}