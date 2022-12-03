package com.jm.service.impl;

import com.jm.common.exception.HxdsException;
import com.jm.common.util.PageUtils;
import com.jm.mapper.RoleMapper;
import com.jm.pojo.RoleEntity;
import com.jm.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 15:09
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public ArrayList<HashMap> searchAllRole() {
        ArrayList<HashMap> list = roleMapper.searchAllRole();
        return list;
    }

    @Override
    public HashMap searchById(Integer id) {
        HashMap map = roleMapper.searchById(id);
        return map;
    }

    @Override
    public PageUtils searchRoleByPage(HashMap param) {
        ArrayList<HashMap> list = roleMapper.searchRoleByPage(param);
        long count = roleMapper.searchRoleCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }

    @Override
    public int insert(RoleEntity role) {
        int rows = roleMapper.insert(role);
        return rows;
    }

    @Override
    public int update(RoleEntity role) {
        int rows = roleMapper.update(role);
        return rows;
    }

    @Override
    public ArrayList<Integer> searchUserIdByRoleId(Integer roleId) {
        ArrayList<Integer> list = roleMapper.searchUserIdByRoleId(roleId);
        return list;
    }

    @Override
    public int deleteRoleByIds(Integer[] ids) {
        if (!roleMapper.searchCanDelete(ids)) {
            throw new HxdsException("无法删除关联用户的角色");
        }
        int rows = roleMapper.deleteRoleByIds(ids);
        return rows;
    }
}