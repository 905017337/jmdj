package com.jm.bffdriver.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.bffdriver.controller.form.AcceptNewOrderForm;
import com.jm.bffdriver.feign.OdrServiceApi;
import com.jm.bffdriver.service.OrderService;
import com.jm.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/12 10:45
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OdrServiceApi odrServiceApi;

    @Override
    @LcnTransaction
    @Transactional
    public String acceptNewOrder(AcceptNewOrderForm form) {
        R r = odrServiceApi.acceptNewOrder(form);
        String result = MapUtil.getStr(r, "result");
        return result;
    }
}