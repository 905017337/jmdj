package com.jm.bffdriver.service;

import com.jm.bffdriver.controller.form.ClearNewOrderQueueForm;
import com.jm.bffdriver.controller.form.ReceiveNewOrderMessageForm;

import java.util.ArrayList;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/9 16:04
 */
public interface NewOrderMessageService {

    ArrayList receiveNewOrderMessage(ReceiveNewOrderMessageForm form);

    void clearNewOrderQueue(ClearNewOrderQueueForm form_2);
}