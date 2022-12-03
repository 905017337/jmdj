package com.jm.bffdriver.feign;

import com.jm.bffdriver.controller.form.SearchDriverTodayBusinessDataForm;
import com.jm.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/2 16:13
 */
@FeignClient(value = "jmdj-odr")
public interface OdrServiceApi {

    @PostMapping("/order/searchDriverTodayBusinessData")
    R searchDriverTodayBusinessData(SearchDriverTodayBusinessDataForm form);
}