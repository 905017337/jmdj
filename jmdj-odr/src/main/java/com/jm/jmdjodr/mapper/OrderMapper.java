package com.jm.jmdjodr.mapper;

import com.jm.jmdjodr.pojo.OrderEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 20:06
 */
public interface OrderMapper {

    int insert(OrderEntity entity);

    String searchOrderIdByUUID(String uuid);

    HashMap searchDriverTodayBusinessData(long driverId);

    int acceptNewOrder(Map param);

    HashMap searchDriverExecuteOrder(Map param);

    Integer searchOrderStatus(Map param);

    int deleteUnAcceptOrder(Map param);

    HashMap searchDriverCurrentOrder(long driverId);

    Long hasCustomerUnFinishedOrder(long customerId);

    HashMap hasCustomerUnAcceptOrder(long customerId);

    HashMap searchOrderForMoveById(Map param);
}