package com.jm.service;

import com.jm.common.util.PageUtils;
import com.jm.pojo.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 12:43
 */
public interface UserService {

    Integer login(Map param);

    Set<String> searchUserPermissions(Integer userId);

    HashMap searchUserSummary(int userId);

    HashMap searchById(Integer userId);

    ArrayList<HashMap> searchAllUser();

    int updatePassword(Map param);

    PageUtils searchUserByPage(Map param);

    int insert(UserEntity user);

    int update(Map param);

    int deleteUserByIds(Integer[] ids);

    HashMap searchNameAndDept(int userId);
}