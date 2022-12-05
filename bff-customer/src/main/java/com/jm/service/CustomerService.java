package com.jm.service;

import com.jm.controller.form.LoginForm;
import com.jm.controller.form.RegisterNewCustomerForm;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 15:43
 */
public interface CustomerService {

    long registerNewCustomer(RegisterNewCustomerForm form);

    Long login(LoginForm form);
}