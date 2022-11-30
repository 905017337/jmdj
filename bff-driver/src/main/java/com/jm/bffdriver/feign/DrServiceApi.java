package com.jm.bffdriver.feign;

import com.jm.bffdriver.controller.form.RegisterNewDriverForm;
import com.jm.bffdriver.controller.form.UpdateDriverAuthForm;
import com.jm.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

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

}