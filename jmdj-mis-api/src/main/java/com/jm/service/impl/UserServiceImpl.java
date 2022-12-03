package com.jm.service.impl;

import com.jm.common.util.PageUtils;
import com.jm.mapper.UserMapper;
import com.jm.pojo.UserEntity;
import com.jm.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 12:43
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public Integer login(Map param) {
        Integer userId = userMapper.login(param);
        return userId;
    }

    @Override
    public Set<String> searchUserPermissions(Integer userId) {
        Set<String> permissions = userMapper.searchUserPermissions(userId);
        return permissions;
    }

    @Override
    public HashMap searchUserSummary(int userId) {
        HashMap map = userMapper.searchUserSummary(userId);
        return map;
    }

    @Override
    public HashMap searchById(Integer userId) {
        HashMap map = userMapper.searchById(userId);
        return map;
    }

    @Override
    public ArrayList<HashMap> searchAllUser() {
        ArrayList<HashMap> list = userMapper.searchAllUser();
        return list;
    }

    @Override
    public int updatePassword(Map param) {
        int rows = userMapper.updatePassword(param);
        return rows;
    }

    @Override
    public PageUtils searchUserByPage(Map param) {
        ArrayList<HashMap> list = userMapper.searchUserByPage(param);
        long count = userMapper.searchUserCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }

    @Override
    public int insert(UserEntity user) {
        int rows = userMapper.insert(user);
        return rows;
    }

    @Override
    public int update(Map param) {
        int rows = userMapper.update(param);
        return rows;
    }

    @Override
    public int deleteUserByIds(Integer[] ids) {
        int rows = userMapper.deleteUserByIds(ids);
        return rows;
    }

    @Override
    public HashMap searchNameAndDept(int userId) {
        HashMap map = userMapper.searchNameAndDept(userId);
        return map;
    }
}