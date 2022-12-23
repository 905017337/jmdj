package com.jm.bffdriver.service;

import com.jm.bffdriver.controller.form.RemoveLocationCacheForm;
import com.jm.bffdriver.controller.form.UpdateLocationCacheForm;
import com.jm.bffdriver.controller.form.UpdateOrderLocationCacheForm;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/10 13:11
 */
public interface DriverLocationService {

    void updateLocationCache(UpdateLocationCacheForm form);

    void removeLocationCache(RemoveLocationCacheForm form);

    void updateOrderLocationCache(UpdateOrderLocationCacheForm form);

}