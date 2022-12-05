package com.jm.service;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 13:54
 */
public interface MapService {

    HashMap estimateOrderMileageAndMinute(String mode,
                                          String startPlaceLatitude,
                                          String startPlaceLongitude,
                                          String endPlaceLatitude,
                                          String endPlaceLongitude);
    HashMap calculateDriverLine(String startPlaceLatitude,
                                String startPlaceLongitude,
                                String endPlaceLatitude,
                                String endPlaceLongitude);
}