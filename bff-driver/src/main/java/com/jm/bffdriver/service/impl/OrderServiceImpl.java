package com.jm.bffdriver.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.bffdriver.controller.form.AcceptNewOrderForm;
import com.jm.bffdriver.controller.form.SearchCustomerInfoInOrderForm;
import com.jm.bffdriver.controller.form.SearchDriverExecuteOrderForm;
import com.jm.bffdriver.feign.CstServiceApi;
import com.jm.bffdriver.feign.OdrServiceApi;
import com.jm.bffdriver.service.OrderService;
import com.jm.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/12 10:45
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OdrServiceApi odrServiceApi;

    @Resource
    private CstServiceApi cstServiceApi;
    @Override
    @LcnTransaction
    @Transactional
    public String acceptNewOrder(AcceptNewOrderForm form) {
        R r = odrServiceApi.acceptNewOrder(form);
        String result = MapUtil.getStr(r, "result");
        return result;
    }

    @Override
    public HashMap searchDriverExecuteOrder(SearchDriverExecuteOrderForm form) {
        //查询订单信息
        R r = odrServiceApi.searchDriverExecuteOrder(form);
        HashMap orderMap = (HashMap)r.get("result");

        //查询代驾客户信息
        long customerId = MapUtil.getLong(orderMap,"customerId");
        SearchCustomerInfoInOrderForm inOrderForm = new SearchCustomerInfoInOrderForm();
        inOrderForm.setCustomerId(customerId);
        r = cstServiceApi.searchCustomerInfoInOrder(inOrderForm);
        HashMap cstMap = (HashMap) r.get("result");

        HashMap map = new HashMap<>();
        map.putAll(orderMap);
        map.putAll(cstMap);
        return  map;
    }
}