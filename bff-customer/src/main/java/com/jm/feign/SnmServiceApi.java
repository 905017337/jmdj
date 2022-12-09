package com.jm.feign;

import com.jm.common.util.R;
import com.jm.controller.form.SendNewOrderMessageForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/9 15:35
 */
@FeignClient(value = "jmdj-snm")
public interface SnmServiceApi {

    @PostMapping("/message/order/new/sendNewOrderMessageAsync")
    R sendNewOrderMessageAsync(SendNewOrderMessageForm form);
}