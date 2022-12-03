package com.jm.mapper;

import com.jm.pojo.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 15:14
 */
@Mapper
public interface RoleMapper {

    ArrayList<HashMap> searchAllRole();

    HashMap searchById(Integer id);

    ArrayList<HashMap> searchRoleByPage(HashMap param);

    long searchRoleCount(HashMap param);

    int insert(RoleEntity role);

    int update(RoleEntity role);

    ArrayList<Integer> searchUserIdByRoleId(Integer roleId);

    boolean searchCanDelete(Integer[] ids);

    int deleteRoleByIds(Integer[] ids);
}