package com.jm.task;

import com.jm.pojo.NewOrderMessage;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/8 19:25
 */
@Component
@Slf4j
public class NewOrderMessageTask {

    @Resource
    private ConnectionFactory factory;


}