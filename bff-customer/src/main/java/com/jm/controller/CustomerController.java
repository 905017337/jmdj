package com.jm.controller;

import cn.dev33.satoken.stp.StpUtil;

import com.jm.common.util.R;
import com.jm.controller.form.LoginForm;
import com.jm.controller.form.RegisterNewCustomerForm;
import com.jm.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@Tag(name = "CustomerController", description = "客户Web接口")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/registerNewCustomer")
    @Operation(summary = "注册新司机")
    public R registerNewCustomer(@RequestBody @Valid RegisterNewCustomerForm form) {
        long customerId = customerService.registerNewCustomer(form);
        StpUtil.login(customerId);
        String token = StpUtil.getTokenInfo().getTokenValue();
        return R.ok().put("token", token);
    }

    @PostMapping("/login")
    @Operation(summary = "登陆系统")
    public R login(@RequestBody @Valid LoginForm form) {
        Long customerId = customerService.login(form);
        if (customerId != null) {
            StpUtil.login(customerId);
            String token = StpUtil.getTokenInfo().getTokenValue();
            return R.ok().put("token", token);
        }
        return R.ok();
    }

}
