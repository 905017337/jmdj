package com.jm.bffdriver.feign;

import com.jm.bffdriver.controller.form.ClearNewOrderQueueForm;
import com.jm.bffdriver.controller.form.ReceiveNewOrderMessageForm;
import com.jm.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/9 16:02
 */
@FeignClient(value = "jmdj-snm")
public interface SnmServiceApi {

    @PostMapping("/message/order/new/receiveNewOrderMessage")
    R receiveNewOrderMessage(ReceiveNewOrderMessageForm form);

    @PostMapping("/message/order/new/clearNewOrderQueue")
    void clearNewOrderQueue(ClearNewOrderQueueForm form);
}