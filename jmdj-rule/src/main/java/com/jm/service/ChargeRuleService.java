package com.jm.service;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 19:49
 */
public interface ChargeRuleService {

    HashMap calculateOrderCharge(String mileage, String time);
}