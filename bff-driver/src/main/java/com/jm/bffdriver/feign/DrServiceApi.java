package com.jm.bffdriver.feign;

import com.jm.bffdriver.controller.form.RegisterNewDriverForm;
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

    @PostMapping("/driver/registerNewDriver")
    R RegisterNewDriver(RegisterNewDriverForm form);



}