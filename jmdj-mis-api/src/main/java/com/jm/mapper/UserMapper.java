package com.jm.mapper;

import com.jm.pojo.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 12:45
 */
@Mapper
public interface UserMapper {

    Integer login(Map param);

    Set<String> searchUserPermissions(Integer userId);

    HashMap searchUserSummary(int userId);

    HashMap searchById(Integer userId);

    ArrayList<HashMap> searchAllUser();

    HashMap searchNameAndDept(int userId);

    int updatePassword(Map param);

    ArrayList<HashMap> searchUserByPage(Map param);

    long searchUserCount(Map param);

    int insert(UserEntity user);

    int update(Map param);

    int deleteUserByIds(Integer[] ids);
}