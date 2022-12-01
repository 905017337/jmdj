package com.jm.bffdriver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.jm.bffdriver.controller.form.CreateDriverFaceModelForm;
import com.jm.bffdriver.controller.form.LoginForm;
import com.jm.bffdriver.controller.form.RegisterNewDriverForm;
import com.jm.bffdriver.controller.form.UpdateDriverAuthForm;
import com.jm.bffdriver.service.DriverService;
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
}