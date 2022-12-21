package com.jm.service;

import com.jm.controller.form.SearchOrderLocationCacheForm;

import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/21 12:32
 */
public interface OrderLocationService {

    HashMap searchOrderLocationCache(SearchOrderLocationCacheForm form);
}