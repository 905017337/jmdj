package com.jm.service.impl;

import com.jm.common.util.R;
import com.jm.controller.form.SearchOrderLocationCacheForm;
import com.jm.feign.MpsServiceApi;
import com.jm.service.OrderLocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/21 12:33
 */
@Service
public class OrderlocationServiceImpl implements OrderLocationService {

    @Resource
    private MpsServiceApi mpsServiceApi;

    @Override
    public HashMap searchOrderLocationCache(SearchOrderLocationCacheForm form) {
        R r = mpsServiceApi.searchOrderLocationCache(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }
}