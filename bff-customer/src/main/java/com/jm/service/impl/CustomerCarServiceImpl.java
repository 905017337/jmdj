package com.jm.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;

import com.jm.common.util.R;
import com.jm.controller.form.DeleteCustomerCarByIdForm;
import com.jm.controller.form.InsertCustomerCarForm;
import com.jm.controller.form.SearchCustomerCarListForm;
import com.jm.feign.CstServiceApi;
import com.jm.service.CustomerCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CustomerCarServiceImpl implements CustomerCarService {

    @Resource
    private CstServiceApi cstServiceApi;

    @Override
    @Transactional
    @LcnTransaction
    public void insertCustomerCar(InsertCustomerCarForm form) {
        cstServiceApi.insertCustomerCar(form);
    }

    @Override
    public ArrayList<HashMap> searchCustomerCarList(SearchCustomerCarListForm form) {
        R r = cstServiceApi.searchCustomerCarList(form);
        ArrayList<HashMap> list = (ArrayList<HashMap>) r.get("result");
        return list;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int deleteCustomerCarById(DeleteCustomerCarByIdForm form) {
        R r = cstServiceApi.deleteCustomerCarById(form);
        int rows = MapUtil.getInt(r, "rows");
        return rows;
    }
}
