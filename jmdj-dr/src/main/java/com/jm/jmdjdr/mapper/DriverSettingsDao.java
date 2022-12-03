package com.jm.jmdjdr.mapper;


import com.jm.jmdjdr.pojo.DriverSettingsEntity;

/**
 * @author caozhenhao
 * @date 2022/11/28 18:14
 * @version 1.0
 */ 
public interface DriverSettingsDao {

    void insertDriverSettings(DriverSettingsEntity entity);

    String searchDriverSettings(long driverId);
}