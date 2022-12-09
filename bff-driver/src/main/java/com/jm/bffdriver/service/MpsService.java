package com.jm.bffdriver.service;

import com.jm.bffdriver.controller.form.RemoveLocationCacheForm;
import com.jm.bffdriver.controller.form.UpdateLocationCacheForm;
import com.jm.common.util.R;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/8 09:38
 */
public interface MpsService {

    void updateLocationCache(UpdateLocationCacheForm form);

    void removeLocationCache(RemoveLocationCacheForm form);
}