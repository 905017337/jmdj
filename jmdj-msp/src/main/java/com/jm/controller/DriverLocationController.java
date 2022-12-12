package com.jm.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jm.common.util.R;
import com.jm.controller.form.*;
import com.jm.service.DriverLocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/7 21:31
 */
@RestController
@RequestMapping("/driver/location")
@Tag(name = "DriverLocationController",description = "司机定位服务web接口")
public class DriverLocationController {

    @Resource
    private DriverLocationService driverLocationService;

    @PostMapping("/updateLocationCache")
    @Operation(summary = "更新司机GPS定位缓存")
    public R updateLocationCache(@RequestBody @Valid UpdateLocationCacheForm form){
        //更新新的定位缓存
        Map param = BeanUtil.beanToMap(form);
        driverLocationService.updateLocationCache(param);
        return R.ok();
    }

    @PostMapping("/removeLocationCache")
    @Operation(summary = "删除司机GPS定位缓存")
    public R removeLocationCache(@RequestBody @Valid RemoveLocationCacheForm form){
        driverLocationService.removeLocationCache(form.getDriverId());
        return R.ok();
    }
    
    @PostMapping("/searchBefittingDriverAboutOrder")
    @Operation(summary = "查询符合谋个订单接单的司机列表")
    public R searchBefittingDriverAboutOrder(@RequestBody @Valid SearchBefittingDriverAboutOrderForm form){
        double startPlaceLatitude = Double.parseDouble(form.getStartPlaceLatitude());
        double startPlaceLongititude = Double.parseDouble(form.getStartPlaceLongitude());
        double endPlaceLatitude = Double.parseDouble(form.getEndPlaceLatitude());
        double endPlaceLongitude = Double.parseDouble(form.getEndPlaceLongitude());
        double mileage = Double.parseDouble(form.getMileage());
         ArrayList result = driverLocationService.searchBefittingDriverAboutOrder(startPlaceLatitude, startPlaceLongititude,
                endPlaceLatitude, endPlaceLongitude, mileage);
         return R.ok().put("result",result);

    }

    @PostMapping("/updateOrderLocationCache")
    @Operation(summary = "更新订单定位缓存")
    public R updateOrderLocationCache(@RequestBody @Valid UpdateOrderLocationCacheForm form){
        Map param = BeanUtil.beanToMap(form);
        driverLocationService.updateOrderLocationCache(param);
        return R.ok();
    }

    @PostMapping("/searchOrderLocationCache")
    @Operation(summary = "查询订单定位缓存")
    public R searchOrderLocationCache(@RequestBody @Valid SearchOrderLocationCacheForm form){
        HashMap map = driverLocationService.searchOrderLocationCache(form.getOrderId());
        return R.ok().put("result",map);
    }
}