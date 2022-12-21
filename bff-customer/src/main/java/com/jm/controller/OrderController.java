package com.jm.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.jm.common.util.R;
import com.jm.controller.form.*;
import com.jm.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/8 14:44
 */
@RestController
@RequestMapping("/order")
@Tag(name = "OrderController",description = "订单模块web接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/createNewOrder")
    @Operation(summary = "创建新订单")
    @SaCheckLogin
    public R createNewOrder(@RequestBody @Valid CreateNewOrderForm form){
        long customerId = StpUtil.getLoginIdAsLong();
        form.setCustomerId(customerId);
         HashMap result = orderService.createNewOrder(form);
         return R.ok().put("result",result);
    }

    @PostMapping("/searchOrderStatus")
    @Operation(summary = "查询订单状态")
    @SaCheckLogin
    public R searchOrderStatus(@RequestBody @Valid SearchOrderStatusForm form){
        long customerId = StpUtil.getLoginIdAsLong();
        form.setCustomerId(customerId);
        Integer status = orderService.searchOrderStatus(form);
        return R.ok().put("result",status);
    }

    @PostMapping("/deleteUnAcceptOrder")
    @Operation(summary = "关闭没有司机接单的订单")
    @SaCheckLogin
    public R DeleteUnAcceptOrder(@RequestBody @Valid DeleteUnAcceptOrderForm form){
        long customerId = StpUtil.getLoginIdAsLong();
        form.setCustomerId(customerId);
        String result = orderService.deleteUnAcceptOrder(form);
        return R.ok().put("result",result);
    }

    @PostMapping("/hasCustomerCurrentOrder")
    @Operation(summary = "查询乘客是否存在当前的订单")
    @SaCheckLogin
    public R hasCustomerCurrentOrder(){
        long customerId = StpUtil.getLoginIdAsLong();
        HasCustomerCurrentOrderForm form = new HasCustomerCurrentOrderForm();
        form.setCustomerId(customerId);
        HashMap map = orderService.hasCustomerCurrentOrder(form);
        return R.ok().put("result",map);
    }

    @PostMapping("/searchOrderForMoveById")
    @SaCheckLogin
    @Operation(summary = "查询订单信息用于司乘同显功能")
    public R searchOrderForMoveById(@RequestBody @Valid SearchOrderForMoveByIdForm form) {
        long customerId = StpUtil.getLoginIdAsLong();
        form.setCustomerId(customerId);
        HashMap map = orderService.searchOrderForMoveById(form);
        return R.ok().put("result", map);
    }
}