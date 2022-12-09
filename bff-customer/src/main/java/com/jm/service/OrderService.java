package com.jm.service;

import com.jm.controller.form.CreateNewOrderForm;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 21:10
 */
public interface OrderService {

    HashMap createNewOrder(CreateNewOrderForm form);
}