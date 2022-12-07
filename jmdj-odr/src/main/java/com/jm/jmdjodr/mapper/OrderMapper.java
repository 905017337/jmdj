package com.jm.jmdjodr.mapper;

import com.jm.jmdjodr.pojo.OrderEntity;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 20:06
 */
public interface OrderMapper {

    int insert(OrderEntity entity);

    String searchOrderIdByUUID(String uuid);

    HashMap searchDriverTodayBusinessData(long driverId);

}