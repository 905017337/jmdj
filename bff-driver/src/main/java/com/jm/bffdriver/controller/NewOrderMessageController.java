package com.jm.bffdriver.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.jm.bffdriver.controller.form.ReceiveNewOrderMessageForm;
import com.jm.bffdriver.service.NewOrderMessageService;
import com.jm.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/9 16:06
 */
@RestController
@RequestMapping("/message/order/new")
@Tag(name = "NewOrderMessageService",description = "新订单消息web接口")
public class NewOrderMessageController {

    @Resource
    private NewOrderMessageService newOrderMessageService;

    @PostMapping("/receiveNewOrderMessage")
    @Operation(summary = "同步接收新订单消息")
    public R receiveNewOrderMessage(){
         long driverId = StpUtil.getLoginIdAsLong();
         ReceiveNewOrderMessageForm form = new ReceiveNewOrderMessageForm();
         form.setUserId(driverId);
        ArrayList list = newOrderMessageService.receiveNewOrderMessage(form);
        return R.ok().put("result",list);

    }
}