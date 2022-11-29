package com.jm.bffdriver.service;

import com.jm.bffdriver.controller.form.RegisterNewDriverForm;

/**
 * @author caozhenhao
 * @date 2022/11/28 21:58
 * @version 1.0
 */ 
public interface DriverService {

    long registerNewDriver(RegisterNewDriverForm form);
}