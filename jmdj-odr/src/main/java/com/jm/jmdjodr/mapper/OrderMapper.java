package com.jm.jmdjodr.mapper;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 20:06
 */
public interface OrderMapper {

    HashMap searchDriverTodayBusinessData(long driverId);
}