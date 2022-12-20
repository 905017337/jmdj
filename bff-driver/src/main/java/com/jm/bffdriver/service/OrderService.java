package com.jm.bffdriver.service;

import com.jm.bffdriver.controller.form.AcceptNewOrderForm;
import com.jm.bffdriver.controller.form.SearchDriverCurrentOrderForm;
import com.jm.bffdriver.controller.form.SearchDriverExecuteOrderForm;
import com.jm.bffdriver.controller.form.SearchOrderForMoveByIdForm;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/12 10:45
 */
public interface OrderService {

    String acceptNewOrder(AcceptNewOrderForm form);

    HashMap searchDriverExecuteOrder(SearchDriverExecuteOrderForm form);

    HashMap searchDriverCurrentOrder(SearchDriverCurrentOrderForm form);

    HashMap searchOrderForMoveById(SearchOrderForMoveByIdForm form);
}
