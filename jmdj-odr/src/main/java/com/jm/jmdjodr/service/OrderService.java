package com.jm.jmdjodr.service;

import com.jm.jmdjodr.pojo.OrderBillEntity;
import com.jm.jmdjodr.pojo.OrderEntity;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 20:12
 */
public interface OrderService {

    HashMap searchDriverTodayBusinessData(long driverId);

    String insertOrder(OrderEntity orderEntity, OrderBillEntity orderBillEntity);
}