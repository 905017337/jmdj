package com.jm.service;

import com.jm.common.util.PageUtils;
import com.jm.pojo.RoleEntity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 15:09
 */
public interface RoleService {

    ArrayList<HashMap> searchAllRole();

    HashMap searchById(Integer id);

    PageUtils searchRoleByPage(HashMap param);

    int insert(RoleEntity role);

    int update(RoleEntity role);

    ArrayList<Integer> searchUserIdByRoleId(Integer id);

    int deleteRoleByIds(Integer[] ids);
}