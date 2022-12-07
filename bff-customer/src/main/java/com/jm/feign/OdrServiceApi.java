package com.jm.feign;

import com.jm.common.util.R;
import com.jm.controller.form.InsertOrderForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 21:12
 */
@FeignClient(value = "jmdj-odr")
public interface OdrServiceApi {

    @PostMapping("/order/insertOrder")
    public R insertOrder(InsertOrderForm form);

}