package com.jm.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.common.exception.HxdsException;
import com.jm.common.util.MicroAppUtil;
import com.jm.mapper.CustomerMapper;
import com.jm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 15:53
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private MicroAppUtil microAppUtil;

    @Override
    @Transactional
    @LcnTransaction
    public String registerNewCustomer(Map param) {
        String code = MapUtil.getStr(param, "code");
        String openId = microAppUtil.getOpenId(code);
        HashMap tempParam = new HashMap() {{
            put("openId", openId);
        }};
        if (customerMapper.hasCustomer(tempParam) != 0) {
            throw new HxdsException("该微信无法注册");
        }

        param.put("openId", openId);
        customerMapper.registerNewCustomer(param);
        String customerId = customerMapper.searchCustomerId(openId);
        return customerId;
    }

    @Override
    public String login(String code) {
        String openId = microAppUtil.getOpenId(code);
        String customerId = customerMapper.login(openId);
        customerId = (customerId != null ? customerId : "");
        return customerId;
    }
}