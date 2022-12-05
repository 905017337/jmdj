package com.jm.feign;

import com.jm.common.util.R;
import com.jm.controller.form.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 15:44
 */
@FeignClient(value = "jmdj-cst")
public interface CstServiceApi {

    @PostMapping("/customer/registerNewCustomer")
    R registerNewCustomer(RegisterNewCustomerForm form);

    @PostMapping("/customer/login")
    R login(LoginForm form);


    @PostMapping("/customer/car/insertCustomerCar")
    R insertCustomerCar(InsertCustomerCarForm form);

    @PostMapping("/customer/car/searchCustomerCarList")
    R searchCustomerCarList(SearchCustomerCarListForm form);

    @PostMapping("/customer/car/deleteCustomerCarById")
    R deleteCustomerCarById(DeleteCustomerCarByIdForm form);
}