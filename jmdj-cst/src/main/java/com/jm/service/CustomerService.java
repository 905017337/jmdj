package com.jm.service;

import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 15:52
 */
public interface CustomerService {

    String registerNewCustomer(Map param);

    String login(String code);
}