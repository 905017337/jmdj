package com.jm.bffdriver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.jm.bffdriver.controller.form.*;
import com.jm.bffdriver.service.DriverLocationService;
import com.jm.bffdriver.service.DriverService;
import com.jm.bffdriver.service.MpsService;
import com.jm.bffdriver.service.NewOrderMessageService;
import com.jm.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @date 2022/11/28 21:25
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController",description = "司机模块web接口")
public class DriverController {

    @Resource
    private DriverService driverService;

    @Resource
    private DriverLocationService driverLocationService;

    @Resource
    private NewOrderMessageService newOrderMessageService;
    @Resource
    private MpsService mpsService;

    @GetMapping("/")
    public String test(){
        return "访问正常";
    }


    @PostMapping("/registerNewDriver")
    public R reg(@RequestBody @Valid RegisterNewDriverForm form){
        long driverId  = driverService.registerNewDriver(form);
        //在saToken上面执行登录，实际上就是缓存userId,然后才有资格拿到令牌
        StpUtil.login(driverId);
        //生成Token令牌字符串（已经加密）
        String token = StpUtil.getTokenInfo().getTokenValue();
        return R.ok().put("token",token);
    }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新实名认证信息")
    @SaCheckLogin
    public R updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form){
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        int rows = driverService.updateDriverAuth(form);
        return R.ok().put("rows",rows);
    }

    @PostMapping("/createDriverFaceModel")
    @Operation(summary = "创建司机人脸模型归档")
    @SaCheckLogin
    public R createDriverFaceModel(@RequestBody @Valid CreateDriverFaceModelForm form){
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        String result = driverService.createDriverFaceModel(form);
        return R.ok().put("result",result);
    }

    @PostMapping("/login")
    @Operation(summary = "登录系统")
    public R login(@RequestBody @Valid LoginForm form){
        HashMap map = driverService.login(form);
        if (map != null){
            Long driverId = MapUtil.getLong(map, "id");
            byte realAuth = Byte.parseByte(MapUtil.getStr(map, "realAuth"));
            Boolean archive = MapUtil.getBool(map, "archive");
            StpUtil.login(driverId);
            String token = StpUtil.getTokenInfo().getTokenValue();
            return R.ok().put("token",token).put("realAuth",realAuth).put("archive",archive);
        }
        return R.ok();
    }

    @PostMapping("/searchDriverBaseInfo")
    @Operation(summary = "查询司机的基本信息")
    @SaCheckLogin
    public R searchDriverBaseInfo(){
        long driverId = StpUtil.getLoginIdAsLong();
        SearchDriverBaseInfoForm form = new SearchDriverBaseInfoForm();
        form.setDriverId(driverId);
        HashMap map = driverService.searchDriverBaseInfo(form);
        return R.ok().put("result",map);
    }
    @PostMapping("/searchWorkbenchData")
    @Operation(summary = "查询司机工作台数据")
    @SaCheckLogin
    public R searchWorkbenchData(){
        long driverId = StpUtil.getLoginIdAsLong();
        HashMap result = driverService.searchWorkBeanData(driverId);
        return R.ok().put("result",result);
    }

    @GetMapping("/searchDriverAuth")
    @Operation(summary = "查询司机认证信息")
    @SaCheckLogin
    public R searchDriverAuth(){
        long driverId = StpUtil.getLoginIdAsLong();
        SearchDriverAuthForm form = new SearchDriverAuthForm();
        form.setDriverId(driverId);
        HashMap map = driverService.searchDriverAuth(form);
        return R.ok().put("result",map);
    }



    @PostMapping("/location/updateLocationCache")
    @Operation(summary = "更新司机的实时位置")
    public R updateLocationCache(@RequestBody @Valid UpdateLocationCacheForm form){

        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        mpsService.updateLocationCache(form);
        return R.ok();
    }

    @PostMapping("/location/removeLocationCache")
    @Operation(summary = "删除司机的位置")
    public R removeLocationCache(@RequestBody @Valid RemoveLocationCacheForm form){
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        mpsService.removeLocationCache(form);
        return R.ok();
    }

    @PostMapping("/startWork")
    @Operation(summary = "开始接单")
    @SaCheckLogin
    public R startWork(){
        long driverId = StpUtil.getLoginIdAsLong();
        RemoveLocationCacheForm form_1=new RemoveLocationCacheForm();
        form_1.setDriverId(driverId);
        driverLocationService.removeLocationCache(form_1);

        ClearNewOrderQueueForm form_2=new ClearNewOrderQueueForm();
        form_2.setUserId(driverId);
        newOrderMessageService.clearNewOrderQueue(form_2);

        return R.ok();
    }

    @PostMapping("/stopWork")
    @Operation(summary = "停止接单")
    @SaCheckLogin
    public R stopWork(){
        long driverId = StpUtil.getLoginIdAsLong();
        RemoveLocationCacheForm form_1=new RemoveLocationCacheForm();
        form_1.setDriverId(driverId);
        driverLocationService.removeLocationCache(form_1);

        ClearNewOrderQueueForm form_2=new ClearNewOrderQueueForm();
        form_2.setUserId(driverId);
        newOrderMessageService.clearNewOrderQueue(form_2);

        return R.ok();
    }
}