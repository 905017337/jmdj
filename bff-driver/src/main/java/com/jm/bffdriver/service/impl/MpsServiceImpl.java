package com.jm.bffdriver.service.impl;

import com.jm.bffdriver.controller.form.RemoveLocationCacheForm;
import com.jm.bffdriver.controller.form.UpdateLocationCacheForm;
import com.jm.bffdriver.feign.MpsServiceApi;
import com.jm.bffdriver.service.MpsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/8 09:39
 */
@Service
public class MpsServiceImpl implements MpsService {

    @Resource
    private MpsServiceApi mpsServiceApi;
    @Override
    public void updateLocationCache(UpdateLocationCacheForm form) {
       mpsServiceApi.updateLocationCache(form);
    }

    @Override
    public void removeLocationCache(RemoveLocationCacheForm form) {

        mpsServiceApi.removeLocationCache(form);
    }
}