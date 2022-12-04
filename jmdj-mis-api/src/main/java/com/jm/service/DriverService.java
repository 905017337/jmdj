package com.jm.service;

import com.jm.common.util.PageUtils;
import com.jm.controller.form.SearchDriverByPageForm;
import com.jm.controller.form.UpdateDriverRealAuthForm;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 12:29
 */
public interface DriverService {

    PageUtils searchDriverByPage(SearchDriverByPageForm form);

    HashMap searchDriverComprehensiveData(byte realAuth,Long driverId);

    int updateDriverRealAuth(UpdateDriverRealAuthForm param);
}