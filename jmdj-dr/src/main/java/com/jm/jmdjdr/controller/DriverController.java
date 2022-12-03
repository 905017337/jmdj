package com.jm.jmdjdr.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jm.common.util.PageUtils;
import com.jm.common.util.R;
import com.jm.jmdjdr.controller.form.*;
import com.jm.jmdjdr.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/registerNewDriver")
    @Operation(summary = "新司机注册")
     public R RegisterNewDriver(@RequestBody @Valid RegisterNewDriverForm form){
         Map param = BeanUtil.beanToMap(form);
         String userId = driverService.registerNewDriver(param);
         return R.ok().put("userId",userId);
     }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新司机实名认证信息")
    public R updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form){
        Map<String, Object> param = BeanUtil.beanToMap(form);
        int rows = driverService.updateDriverAuth(param);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/createDriverFaceModel")
    @Operation(summary = "创建司机人脸模型归档")
    public R createDriverFaceModel(@RequestBody @Valid CreateDriverFaceModelForm form){
        String result = driverService.createDriverFaceModel(form.getDriverId(),form.getPhoto());
        return R.ok().put("result",result);
    }

    @PostMapping("/login")
    @Operation(summary = "登录系统")
    public R login(@RequestBody  LoginForm form){
        HashMap map = driverService.login(form.getCode());
        return R.ok().put("result",map);
    }


    @PostMapping("/searchDriverBaseInfo")
    @Operation(summary = "查询司机基本信息")
    public R searchDriverBaseInfo(@RequestBody @Valid SearchDriverBaseInfoForm form){
        HashMap result = driverService.searchDriverBaseInfo(form.getDriverId());
        return R.ok().put("result",result);
    }

    @PostMapping("/searchDriverByPage")
    @Operation(summary = "查询司机分页记录")
    public R searchDriverByPage(@RequestBody @Valid SearchDriverByPageForm form){
        Map param = BeanUtil.beanToMap(form);
        int page = form.getPage();
        int length = form.getLength();
        int start = (page - 1) * length;
        param.put("start",start);
        PageUtils result = driverService.searchDriverByPage(param);
        return R.ok().put("result",result);
    }

    @PostMapping("/searchDriverAuth")
    @Operation(summary = "查询司机认证信息")
    public R searchDriverAuth(@RequestBody @Valid SearchDriverAuthForm form){
        HashMap result = driverService.searchDriverAuth(form.getDriverId());
        return R.ok().put("result",result);
    }

    @PostMapping("/searchDriverRealSummary")
    @Operation(summary = "查询司机实名信息摘要")
    public R searchDriverRealSummary(@RequestBody @Valid SearchDriverRealSummaryForm from){
        HashMap map = driverService.searchDriverRealSummary(from.getDriverId());
        return R.ok().put("result",map);
    }
}