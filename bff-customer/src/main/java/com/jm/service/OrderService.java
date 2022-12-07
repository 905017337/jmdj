package com.jm.service;

import com.jm.controller.form.CreateNewOrderForm;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 21:10
 */
public interface OrderService {

    int createNewOrder(CreateNewOrderForm form);
}