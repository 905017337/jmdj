package com.jm.bffdriver.feign;

import com.jm.bffdriver.controller.form.*;
import com.jm.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @date 2022/11/28 21:47
 * @version 1.0
 */
@FeignClient(value = "jmdj-dr")
public interface DrServiceApi {

    /**
     * 司机注册
     * @param form
     * @return
     */
    @PostMapping("/driver/registerNewDriver")
    R RegisterNewDriver(RegisterNewDriverForm form);

    /**
     * 司机注册信息持久化保存
     * @param form
     * @return
     */
    @PostMapping("/driver/updateDriverAuth")
    R updateDriverAuth(UpdateDriverAuthForm form);

    /**
     * 创建司机面部识别
     * @param form
     * @return
     */
    @PostMapping("/driver/createDriverFaceModel")
    R createDriverFaceModel(CreateDriverFaceModelForm form);

    /**
     * 司机登录系统
     * @param form
     * @return
     */
    @PostMapping("/driver/login")
    R login(LoginForm form);

    /**
     * 获取司机的基本信息
     * @param driverId
     * @return
     */
    @PostMapping("/driver/searchDriverBaseInfo")
    R searchDriverBaseInfo(SearchDriverBaseInfoForm driverId);

    /**
     * 获取司机端配置
     * @param form
     * @return
     */
    @PostMapping("/settings/searchDriverSettings")
    R searchDriverSettings(SearchDriverSettingsForm form);

    /**
     * 获取司机的个人信息
     * @param form
     * @return
     */
    @PostMapping("/driver/searchDriverAuth")
    R searchDriverAuth(SearchDriverAuthForm form);
}