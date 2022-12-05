package com.jm.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.jm.mapper.CustomerCarMapper;
import com.jm.pojo.CustomerCarEntity;
import com.jm.service.CustomerCarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CustomerCarServiceImpl implements CustomerCarService {

    @Resource
    private CustomerCarMapper customerCarMapper;

    @Override
    @Transactional
    @LcnTransaction
    public void insertCustomerCar(CustomerCarEntity entity) {
        customerCarMapper.insert(entity);

    }

    @Override
    public ArrayList<HashMap> searchCustomerCarList(long customerId) {
        ArrayList list = customerCarMapper.searchCustomerCarList(customerId);
        return list;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int deleteCustomerCarById(long id) {
        int rows = customerCarMapper.deleteCustomerCarById(id);
        return rows;
    }
}
