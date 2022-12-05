package com.jm.mapper;



import com.jm.pojo.CustomerCarEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarMapper {

    public int insert(CustomerCarEntity entity);

    public ArrayList<HashMap> searchCustomerCarList(long customerId);

    public int deleteCustomerCarById(long id);
}




