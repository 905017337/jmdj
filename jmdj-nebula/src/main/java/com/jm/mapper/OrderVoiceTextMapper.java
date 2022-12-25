package com.jm.mapper;

import com.jm.pojo.OrderVoiceTextEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/23 12:44
 */

public interface OrderVoiceTextMapper {

    int insert(OrderVoiceTextEntity entity);
}