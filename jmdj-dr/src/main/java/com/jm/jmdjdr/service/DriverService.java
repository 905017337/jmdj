package com.jm.jmdjdr.service;

import com.jm.common.util.PageUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @date 2022/11/28 18:24
 * @version 1.0
 */ 
public interface DriverService {

    String registerNewDriver(Map param);

    int updateDriverAuth(Map<String, Object> param);

    String createDriverFaceModel(long driverId,String photo);

    HashMap login(String code);

    HashMap searchDriverBaseInfo(long driverId);

    PageUtils searchDriverByPage(Map param);
}