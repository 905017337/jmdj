package com.jm.bffdriver.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.bffdriver.controller.form.ClearNewOrderQueueForm;
import com.jm.bffdriver.controller.form.ReceiveNewOrderMessageForm;
import com.jm.bffdriver.controller.form.StartDrivingForm;
import com.jm.bffdriver.feign.SnmServiceApi;
import com.jm.bffdriver.service.NewOrderMessageService;
import com.jm.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/9 16:04
 */
@Service
public class NewOrderMessageServiceIMpl implements NewOrderMessageService {

    @Resource
    private SnmServiceApi snmServiceApi;
    @Override
    public ArrayList receiveNewOrderMessage(ReceiveNewOrderMessageForm form) {
        R r = snmServiceApi.receiveNewOrderMessage(form);
        ArrayList list = (ArrayList) r.get("result");
        return list;
    }

    @Override
    public void clearNewOrderQueue(ClearNewOrderQueueForm form) {
        snmServiceApi.clearNewOrderQueue(form);
    }


}