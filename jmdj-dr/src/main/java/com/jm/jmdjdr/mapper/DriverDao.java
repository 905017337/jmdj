package com.jm.jmdjdr.mapper;

import java.util.Map;

/**
 * @author caozhenhao
 * @date 2022/11/28 18:05
 * @version 1.0
 */ 
public interface DriverDao {

    int registerNewDriver(Map param);

    long hasDriver(Map param);

    String searchDriverId(String openId);

    int updateDriverAuth(Map<String, Object> param);
}