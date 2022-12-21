package com.jm.controller;

import com.jm.common.util.R;
import com.jm.controller.form.SearchOrderLocationCacheForm;
import com.jm.service.OrderLocationService;
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
 * @date 2022/12/21 12:35
 */
@RestController
@RequestMapping("/order/location")
@Tag(name = "orderLocationController",description = "订单定位服务web接口")
public class OrderLocationController {

    @Resource
    private OrderLocationService orderLocationService;

    @PostMapping("/searchOrderLocationCache")
    @Operation(summary = "查询订单定位缓存")
    public R searchOrderLocationCache(@RequestBody @Valid SearchOrderLocationCacheForm form){
        HashMap map = orderLocationService.searchOrderLocationCache(form);
        return R.ok().put("result",map);
    }
}