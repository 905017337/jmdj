package com.jm.jmdjdr.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jm.common.util.R;
import com.jm.jmdjdr.controller.form.CreateDriverFaceModelForm;
import com.jm.jmdjdr.controller.form.LoginForm;
import com.jm.jmdjdr.controller.form.RegisterNewDriverForm;
import com.jm.jmdjdr.controller.form.UpdateDriverAuthForm;
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

}