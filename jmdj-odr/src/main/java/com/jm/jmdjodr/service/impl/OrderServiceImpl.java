package com.jm.jmdjodr.service.impl;

import com.jm.jmdjodr.mapper.OrderMapper;
import com.jm.jmdjodr.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 20:13
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public HashMap searchDriverTodayBusinessData(long driverId){
        HashMap result = orderMapper.searchDriverTodayBusinessData(driverId);
        return result;
    }
}