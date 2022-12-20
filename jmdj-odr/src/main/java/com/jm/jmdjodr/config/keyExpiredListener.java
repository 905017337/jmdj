package com.jm.jmdjodr.config;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.jmdjodr.mapper.OrderBillMapper;
import com.jm.jmdjodr.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/19 13:36
 */
@Slf4j
@Component
public class keyExpiredListener extends KeyExpirationEventMessageListener {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderBillMapper orderBillMapper;


    public keyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    @Transactional
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("redis销毁数据"+message.toString());
        System.out.println("redis销毁数据111"+message.getBody());
        //从消息队列中接收消息
        System.out.println("new String(message.getChannel())->"+new String(message.getChannel()));
        if(new String(message.getChannel()).equals("__keyevent@5__:expired")){
            System.out.println("----监听失效---");
            //反系列化key,否则出现乱码
//            JdkSerializationRedisSerializer serializer=new JdkSerializationRedisSerializer();
//            String key = serializer.deserialize(message.toString()).toString();
            System.out.println("key==="+message.toString());
            if(message.toString().contains("order#")){
                long orderId = Long.parseLong(message.toString().split("#")[1]);
                System.out.println("orderId->"+orderId);
                HashMap param = new HashMap(){{
                    put("orderId",orderId);
                }};
                int rows = orderMapper.deleteUnAcceptOrder(param);
                if(rows == 1){
                    log.info("删除了无人接单的订单："+orderId);
                }
                rows = orderBillMapper.deleteUnAcceptOrderBill(orderId);
                if(rows == 1){
                    log.info("删除了无人接单的账单："+orderId); ///
                }
            }
        }
        super.onMessage(message, pattern);
    }
}