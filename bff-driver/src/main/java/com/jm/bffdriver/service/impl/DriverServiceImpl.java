package com.jm.bffdriver.service.impl;

import cn.hutool.core.convert.Convert;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.bffdriver.controller.form.RegisterNewDriverForm;
import com.jm.bffdriver.feign.DrServiceApi;
import com.jm.bffdriver.service.DriverService;
import com.jm.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author caozhenhao
 * @date 2022/11/28 21:51
 * @version 1.0
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;


    @Override
    @Transactional
    @LcnTransaction
    public long registerNewDriver(RegisterNewDriverForm form) {
        R r = drServiceApi.RegisterNewDriver(form);
        Long userId = Convert.toLong(r.get("userId"));
        return userId;
    }
}