package com.jm.service.impl;

import com.jm.common.exception.HxdsException;
import com.jm.common.util.PageUtils;
import com.jm.mapper.DeptMapper;
import com.jm.pojo.DeptEntity;
import com.jm.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 14:17
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;
    @Override
    public PageUtils searchDeptByPage(Map param) {
        ArrayList<HashMap> list = deptMapper.searchDeptByPage(param);
        long count = deptMapper.searchDeptCount(param);
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list, count, start, length);
        return pageUtils;
    }

    @Override
    public ArrayList<HashMap> searchAllDept() {
        ArrayList<HashMap> list = deptMapper.searchAllDept();
        return list;
    }

    @Override
    public HashMap searchById(Integer id) {
        HashMap map = deptMapper.searchById(id);
        return map;
    }

    @Override
    public int deleteDeptByIds(Integer[] ids) {
        if (!deptMapper.searchCanDelete(ids)) {
            throw new HxdsException("无法删除关联用户的部门");
        }
        int rows = deptMapper.deleteDeptByIds(ids);
        return rows;
    }

    @Override
    public int update(DeptEntity dept) {
        int rows = deptMapper.update(dept);
        return rows;
    }

    @Override
    public int insert(DeptEntity dept) {
        int rows = deptMapper.insert(dept);
        return rows;
    }
}