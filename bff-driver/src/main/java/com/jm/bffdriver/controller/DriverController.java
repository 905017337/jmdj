package com.jm.bffdriver.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jm.bffdriver.controller.form.RegisterNewDriverForm;
import com.jm.bffdriver.service.DriverService;
import com.jm.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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

//    @PostMapping(value = "/RegisterNewDriver")
//    public R RegisterNewDriver(@RequestBody @Valid RegisterNewDriverForm form){
//        long driverId  = driverService.registerNewDriver(form);
//        //在saToken上面执行登录，实际上就是缓存userId,然后才有资格拿到令牌
//        StpUtil.login(driverId);
//        //生成Token令牌字符串（已经加密）
//        String token = StpUtil.getTokenInfo().getTokenValue();
//        return R.ok().put("token",token);
//    }


    @PostMapping("/registerNewDriver")
    public R reg(@RequestBody RegisterNewDriverForm form){
        long driverId  = driverService.registerNewDriver(form);
        //在saToken上面执行登录，实际上就是缓存userId,然后才有资格拿到令牌
        StpUtil.login(driverId);
        //生成Token令牌字符串（已经加密）
        String token = StpUtil.getTokenInfo().getTokenValue();
        return R.ok().put("token",token);
    }




}