package com.jm.service.impl;

import cn.hutool.json.JSONObject;
import com.jm.service.ChargeRuleService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 19:56
 */
@Service
public class ChargeRuleServiceImpl implements ChargeRuleService {

    @Override
    public HashMap calculateOrderCharge(String mileage, String time) {
        HashMap json = new HashMap<>();
        json.put("amount","115.00");  // 总金额
        json.put("chargeRuleId","7146019160341666785");  //使用的规则ID

        json.put("baseMileage","8"); //代驾基础历程


        json.put("baseMileage","8"); //代驾基础历程
        json.put("baseMileagePrice","85"); //基础历程费
        json.put("exceedMileagePrice","3.5"); //超出规定历程后每公里3.5元
        json.put("mileageFee","102.50"); //本订单历程费用

        json.put("baseMinute","10"); //免费等时10分钟
        json.put("exceedMinutePrice","1.0"); //超出10分钟后，每分钟1元
        json.put("waitingFee","0.00"); //本订单等时费

        json.put("baseReturnMileage","8"); //总历程超过8公里后，要加收返程费
        json.put("exceedReturnPrice","1.0"); //返程历程每公里1元
        json.put("returnMileage","12.5"); //本订单的返程历程
        json.put("returnFee","12.50"); //本订单的返程费

        return json;
    }
}