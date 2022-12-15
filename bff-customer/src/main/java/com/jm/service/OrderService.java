package com.jm.service;

import com.jm.controller.form.CreateNewOrderForm;
import com.jm.controller.form.DeleteUnAcceptOrderForm;
import com.jm.controller.form.SearchOrderStatusForm;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 21:10
 */
public interface OrderService {

    HashMap createNewOrder(CreateNewOrderForm form);

    Integer searchOrderStatus(SearchOrderStatusForm form);

    String deleteUnAcceptOrder(DeleteUnAcceptOrderForm form);
}