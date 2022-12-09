package com.jm.jmdjodr.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.common.exception.HxdsException;
import com.jm.jmdjodr.mapper.OrderBillMapper;
import com.jm.jmdjodr.mapper.OrderMapper;
import com.jm.jmdjodr.pojo.OrderBillEntity;
import com.jm.jmdjodr.pojo.OrderEntity;
import com.jm.jmdjodr.service.OrderService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
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
                redisTemplate.expire("order#"+id,15, TimeUnit.MINUTES); //缓存15分钟
                return id;
            }else {
                throw new HxdsException("保存新订单费用失败");
            }
        }else {
            throw new HxdsException("保存新订单失败");
        }
    }


    @Override
    public HashMap searchDriverTodayBusinessData(long driverId){
        HashMap result = orderMapper.searchDriverTodayBusinessData(driverId);
        return result;
    }


}