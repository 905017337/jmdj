package com.jm.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.common.util.R;
import com.jm.controller.form.LoginForm;
import com.jm.controller.form.RegisterNewCustomerForm;
import com.jm.feign.CstServiceApi;
import com.jm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 15:43
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CstServiceApi cstServiceApi;

    @Override
    @Transactional
    @LcnTransaction
    public long registerNewCustomer(RegisterNewCustomerForm form) {
        R r = cstServiceApi.registerNewCustomer(form);
        long userId = Convert.toLong(r.get("userId"));
        return userId;
    }

    @Override
    public Long login(LoginForm form) {
        R r = cstServiceApi.login(form);
        String userId = MapUtil.getStr(r, "userId");
        if (!StrUtil.isBlank(userId)) {
            return Convert.toLong(userId);
        }
        return null;
    }
}