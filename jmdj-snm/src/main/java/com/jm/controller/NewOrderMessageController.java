package com.jm.controller;

import com.jm.common.util.R;
import com.jm.controller.from.DeleteNewOrderQueueForm;
import com.jm.controller.from.ReceiveNewOrderMessageForm;
import com.jm.controller.from.SendNewOrderMessageForm;
import com.jm.pojo.NewOrderMessage;
import com.jm.task.NewOrderMassageTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/9 10:13
 */
@RestController
@RequestMapping("/message/order/new")
@Tag(name = "NewOrderMEssageController",description = "新订单消息web接口")
public class NewOrderMessageController {

    @Resource
    private NewOrderMassageTask task;

    @PostMapping("/sendNewOrderMessage")
    @Operation(summary = "同步发送新订单消息")
    public R sendNewOrderMessage(@RequestBody @Valid SendNewOrderMessageForm form){
        ArrayList<NewOrderMessage> list = new ArrayList<>();
        String[] driversContent = form.getDriversContent();
        for (String one : driversContent) {
            String[] temp = one.split("#");
            String userId = temp[0];
            String distance = temp[1];
            NewOrderMessage message = new NewOrderMessage();
            message.setUserId(userId);
            message.setOrderId(form.getOrderId().toString());
            message.setFrom(form.getFrom());
            message.setTo(form.getTo());
            message.setMileage(form.getMileage());
            message.setMinute(form.getMinute().toString());
            message.setDistance(distance);
            message.setExpectsFee(form.getExpectsFee());
            message.setFavourFee(form.getFavourFee());
            list.add(message);
        }
        task.sendNewOrderMessage(list);
        return R.ok();
    }

    @PostMapping("/sendNewOrderMessageAsync")
    @Operation(summary = "异步发送新订单消息")
    public R sendNewOrderMessageAsync(@RequestBody @Valid SendNewOrderMessageForm form){
        ArrayList<NewOrderMessage> list = new ArrayList<>();
        String[] driversContent = form.getDriversContent();
        for (String one : driversContent) {
            String[] temp = one.split("#");
            String userId = temp[0];
            String distance = temp[1];
            NewOrderMessage message = new NewOrderMessage();
            message.setUserId(userId);
            message.setOrderId(form.getOrderId().toString());
            message.setFrom(form.getFrom());
            message.setTo(form.getTo());
            message.setMileage(form.getMileage());
            message.setMinute(form.getMinute().toString());
            message.setDistance(distance);
            message.setExpectsFee(form.getExpectsFee());
            message.setFavourFee(form.getFavourFee());
            list.add(message);
        }
        task.sendNewOrderMessageAsync(list);
        return R.ok();
    }

    @PostMapping("/receiveNewOrderMessage")
    @Operation(summary = "同步接收新订单消息")
    public R receiveNewOrderMessage(@RequestBody @Valid ReceiveNewOrderMessageForm form){
        List<NewOrderMessage> result = task.receiveNewOrderMessage(form.getUserId());
        return R.ok().put("result",result);

    }

//    @PostMapping("/deleteNewOrderQueue")
//    @Operation(summary = "同步删除新订单消息队列")
//    public R deleteNewOrderQueue(@RequestBody @Valid DeleteNewOrderQueueForm form){
//        task.deleteNewOrderQueue(form.getUserId());
//
//    }

}