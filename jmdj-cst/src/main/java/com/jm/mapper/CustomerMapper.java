package com.jm.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 15:54
 */
@Mapper
public interface CustomerMapper {

    long hasCustomer(HashMap tempParam);

    void registerNewCustomer(Map param);

    String searchCustomerId(String openId);

    String login(String openId);
}