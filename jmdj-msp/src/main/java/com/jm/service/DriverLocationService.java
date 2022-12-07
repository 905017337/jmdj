package com.jm.service;

import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/7 20:56
 */
public interface DriverLocationService {

    void updateLocationCache(Map param);
    void removeLocationCache(long driverId);

}