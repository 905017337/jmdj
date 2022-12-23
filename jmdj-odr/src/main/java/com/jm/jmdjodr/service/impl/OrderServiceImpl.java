package com.jm.jmdjodr.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.common.exception.HxdsException;
import com.jm.jmdjodr.mapper.OrderBillMapper;
import com.jm.jmdjodr.mapper.OrderMapper;
import com.jm.jmdjodr.pojo.OrderBillEntity;
import com.jm.jmdjodr.pojo.OrderEntity;
import com.jm.jmdjodr.service.OrderService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 20:13
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderBillMapper orderBillMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    @LcnTransaction
    public String insertOrder(OrderEntity orderEntity, OrderBillEntity orderBillEntity) {
        //插入订单记录
        int rows = orderMapper.insert(orderEntity);
        if(rows == 1){
            String id = orderMapper.searchOrderIdByUUID(orderEntity.getUuid());
            //插入订单费用记录
            orderBillEntity.setOrderId(Long.parseLong(id));
            rows = orderBillMapper.insert(orderBillEntity);
            if(rows == 1){
                //往redis里面插入缓存，配合redis事务用于司机抢单，避免多个司机同时抢单成功
                redisTemplate.opsForValue().set("order#"+id,"none");
                redisTemplate.expire("order#"+id,16, TimeUnit.MINUTES); //缓存15分钟
                return id;
            }else {
                throw new HxdsException("保存新订单费用失败");
            }
        }else {
            throw new HxdsException("保存新订单失败");
        }
    }

    @Override
    @Transactional
    @LcnTransaction
    public String acceptNewOrder(long driverId,long orderId) {
        //Redis 不存在抢单的新订单就代表抢单失败
        if(!redisTemplate.hasKey("order#"+orderId)){
            return "抢单失败";
        }
        //执行redis事务
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                //获取新订单的version
                redisOperations.watch("order#"+orderId);
                //本地缓存redis操作
                redisOperations.multi();
                //把新订单缓存的value设置成抢单司机的id
                redisOperations.opsForValue().set("order#"+orderId,driverId);
                //执行Redis事务，如果事务提交失败回自动抛出异常
                return redisOperations.exec();
            }
        });
        //抢单成功后，删除Redis中的新订单，避免让其他司机参与抢单
        redisTemplate.delete("order#"+orderId);
        //更新订单记录，或添加上接单司机ID和接单时间
        HashMap param = new HashMap(){{
            put("driverId",driverId);
            put("orderId",orderId);
        }};
        int rows = orderMapper.acceptNewOrder(param);
        if(rows != 1){
            throw new HxdsException("接单失败，无法更新订单记录");
        }
        return "接单成功";
    }

    @Override
    public HashMap searchDriverExecuteOrder(Map param) {
        final HashMap map = orderMapper.searchDriverExecuteOrder(param);
        return map;
    }

    @Override
    public Integer searchOrderStatus(Map param) {
        Integer status = orderMapper.searchOrderStatus(param);
        if(status == null){
//            throw new HxdsException("没有查到数据，请核对查询条件");
            status = 0;
        }

        return status;
    }

    @Override
    @Transactional
    @LcnTransaction
    public String deleteUnAcceptOrder(Map param) {
        long orderId = MapUtil.getLong(param,"orderId");
        if(!redisTemplate.hasKey("order#"+orderId)){
            return "订单取消失败";
        }
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.watch("order#"+orderId);
                redisOperations.multi();
                redisOperations.opsForValue().set("order#"+orderId,"none");
                return redisOperations.exec();
            }
        });

        redisTemplate.delete("order#"+orderId);
        int rows = orderMapper.deleteUnAcceptOrder(param);
        if(rows != 1){
            return "订单取消失败";
        }
        rows = orderBillMapper.deleteUnAcceptOrderBill(orderId);
        if(rows != 1){
            return "订单取消成功";
        }
        return "订单取消成功";
    }

    @Override
    public HashMap searchDriverCurrentOrder(long driverId) {
        HashMap map = orderMapper.searchDriverCurrentOrder(driverId);
        return map;
    }

    @Override
    public HashMap hasCustomerCurrentOrder(long customerId) {
        HashMap result = new HashMap<>();
        HashMap map = orderMapper.hasCustomerUnAcceptOrder(customerId);
        result.put("hasCustomerUnAcceptOrder",map != null);
        result.put("unAcceptOrder",map);
        Long id = orderMapper.hasCustomerUnFinishedOrder(customerId);
        result.put("hasCustomerUnFinishedOrder",id != null);
        result.put("unFinishedOrder",id);
        return result;
    }

    @Override
    public HashMap searchOrderForMoveById(Map param) {
        HashMap map = orderMapper.searchOrderForMoveById(param);
        return map;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int arraiveStartPlace(Map param) {
        //t添加到达上车点标志位
        long orderId = MapUtil.getLong(param,"orderId");
        redisTemplate.opsForValue().set("order_driver_arrivied#"+orderId,"1");
        int rows = orderMapper.updateOrderStatus(param);
        if(rows != 1){
            throw new HxdsException("更新订单状态失败");
        }
        return rows;
    }

    @Override
    public boolean confirmArriveStartPlace(long orderId) {
        String key = "order_dirver_arrivied#"+orderId;
        if(redisTemplate.hasKey(key) || redisTemplate.opsForValue().get(key).toString().equals("1")){
            redisTemplate.opsForValue().set(key,"2");
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int startDriving(Map param) {
        long orderId = MapUtil.getLong(param,"orderId");
        String key = "order_dirver_arrivied#"+orderId;
        if(redisTemplate.hasKey(key) || redisTemplate.opsForValue().get(key).toString().equals("2")){
            redisTemplate.delete(key);
            int rows = orderMapper.updateOrderStatus(param);
            if(rows != 1){
                throw new HxdsException("更新订单状态失败");
            }
            return rows;
        }
        return 0;
    }


    @Override
    public HashMap searchDriverTodayBusinessData(long driverId){
        HashMap result = orderMapper.searchDriverTodayBusinessData(driverId);
        return result;
    }


}