package com.jm.jmdjdr.mapper;

import java.util.ArrayList;
import java.util.HashMap;
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

    HashMap searchDriverNameAndSex(long driverId);

    int updadteDriverArchive(long driverId);

    HashMap login(String openId);

    HashMap searchDriverBaseInfo(long driverId);

    ArrayList<HashMap> searchDriverByPage(Map param);

    long searchDriverCount(Map param);

    HashMap searchDriverAuth(long driverId);

    HashMap searchDriverRealSummary(long driverId);

    int updateDriverRealAuth(Map param);
}