package com.jm.bffdriver.service;

import com.jm.bffdriver.controller.form.CreateDriverFaceModelForm;
import com.jm.bffdriver.controller.form.LoginForm;
import com.jm.bffdriver.controller.form.RegisterNewDriverForm;
import com.jm.bffdriver.controller.form.UpdateDriverAuthForm;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @date 2022/11/28 21:58
 * @version 1.0
 */ 
public interface DriverService {

    long registerNewDriver(RegisterNewDriverForm form);

    int updateDriverAuth(UpdateDriverAuthForm form);

    String createDriverFaceModel(CreateDriverFaceModelForm form);

    HashMap login(LoginForm form);
}