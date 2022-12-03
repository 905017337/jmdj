package com.jm.jmdjodr.controller;

import com.jm.common.util.R;
import com.jm.jmdjodr.controller.form.SearchDriverTodayBusinessDataForm;
import com.jm.jmdjodr.service.OrderService;
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
 * @date 2022/12/1 20:16
 */
@RestController
@RequestMapping("/order")
@Tag(name = "OrderController",description = "订单模块Web接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/searchDriverTodayBusinessData")
    @Operation(summary = "查询司机当天营业数据")
    public R searchDriverTodayBusinessdata(@RequestBody @Valid SearchDriverTodayBusinessDataForm form){
        HashMap result = orderService.searchDriverTodayBusinessData(form.getDriverId());
        return R.ok().put("result",result);
    }
}