package com.jm.task;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.jm.common.exception.HxdsException;
import com.jm.pojo.NewOrderMessage;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/8 18:35
 */
@Component
@Slf4j
public class NewOrderMassageTask {

    @Resource
    private ConnectionFactory factory;

    /**
     * 同步发送新订单消息
     * @param list
     */
    public void sendNewOrderMessage(ArrayList<NewOrderMessage> list){
        int ttl = 1 * 60 * 1000; //新订单消息缓存过期时间 1分钟
        String exchangeName = "new_order_private"; //交换机名称
        try(
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                ) {

            //定义交换机，根据routing key 路由消息
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            HashMap param = new HashMap<>();
            for (NewOrderMessage message : list) {
                HashMap map = new HashMap(){{
                    //MQ消息的属性信息
                    put("orderId",message.getOrderId());
                    put("from",message.getFrom());
                    put("to",message.getTo());
                    put("expectsFee",message.getExpectsFee());
                    put("mileage",message.getMileage());
                    put("minute",message.getMinute());
                    put("distance",message.getDistance());
                    put("favourFee",message.getFavourFee());
                }};
                //创建消息属性对象
                AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().contentEncoding("UTF-8")
                        .headers(map).expiration(ttl+"").build();

                String queueName = "queue_"+message.getUserId();
                String routingKey = message.getUserId();
                channel.queueDeclare(queueName,true,false,false,param);
                channel.queueBind(queueName,exchangeName,routingKey);
                channel.basicPublish(exchangeName,routingKey,properties,("新订单"+message.getOrderId()).getBytes());
                log.debug(message.getUserId()+"的新订单消息发送成功");
            }
        } catch (Exception e) {
            log.error("执行异常",e);
            throw  new HxdsException("新订单消息发送失败");
        }


    }

    /**
     * 异步发送新订单消息
     */
    @Async
    public void sendNewOrderMessageAsync(ArrayList<NewOrderMessage> list){
        sendNewOrderMessage(list);
    }

    /**
     * 同步接收新订单
     * @param userId
     * @return
     */
    public List<NewOrderMessage> receiveNewOrderMessage(long userId){
        String exchangeName = "new_order_private"; //交换机的名字
        String quereName = "queue_"+userId; //队列名字
        String routingKey = userId+""; //routing key

        final ArrayList<NewOrderMessage> list = new ArrayList<>();
        try(
                Connection connection = factory.newConnection();
                Channel privateChannel = connection.createChannel();
        ){
            //定义交换机  roting key模式
            privateChannel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            //声明队列（持久化缓存消息，消息接收不加锁，消息全部接收完并不删除队列）
            privateChannel.queueDeclare(quereName,true,false,false,null);
            //绑定要接收的队列
            privateChannel.queueBind(quereName,exchangeName,routingKey);
            //为了避免一次性接收太多消息，我们采用限流的方式，每次接收10条数据，然后循环接收
            privateChannel.basicQos(0,10,true);
            while(true){
                final GetResponse response = privateChannel.basicGet(quereName, false);
                if(response != null){
                    final val properties = response.getProps();
                    final val map = properties.getHeaders();
                    String orderId = MapUtil.getStr(map,"orderId");
                    String from = MapUtil.getStr(map,"from");
                    String to = MapUtil.getStr(map,"to");
                    String expectsFee = MapUtil.getStr(map,"expectsFee");
                    String  mileage = MapUtil.getStr(map,"mileage");
                    String minute = MapUtil.getStr(map,"distance");
                    String distance = MapUtil.getStr(map,"distance");
                    String favourFee = MapUtil.getStr(map,"favourFee");

                    final NewOrderMessage message = new NewOrderMessage();
                    message.setOrderId(orderId);
                    message.setFrom(from);
                    message.setTo(to);
                    message.setExpectsFee(expectsFee);
                    message.setMileage(mileage);
                    message.setMinute(minute);
                    message.setDistance(distance);
                    message.setFavourFee(favourFee);

                    list.add(message);

                    byte[] body = response.getBody();
                    String msg = new String(body);
                    log.debug("从rabbitMq接收的订单信息："+msg);
                    //给rabbitmq返回标志位  代表接收成功
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    privateChannel.basicAck(deliveryTag,false);


                }else {
                    break;
                }
            }
            ListUtil.reverse(list);
            return list;

        }catch (Exception e){

        }

        return  null;

    }
}