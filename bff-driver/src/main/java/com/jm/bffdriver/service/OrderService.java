package com.jm.bffdriver.service;

import com.jm.bffdriver.controller.form.AcceptNewOrderForm;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/12 10:45
 */
public interface OrderService {

    String acceptNewOrder(AcceptNewOrderForm form);
}
