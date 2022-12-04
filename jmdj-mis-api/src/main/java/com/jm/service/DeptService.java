package com.jm.service;

import com.jm.common.util.PageUtils;
import com.jm.controller.form.UpdateDriverRealAuthForm;
import com.jm.pojo.DeptEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 14:17
 */
public interface DeptService {

    PageUtils searchDeptByPage(Map param);

    ArrayList<HashMap> searchAllDept();

    HashMap searchById(Integer id);

    int deleteDeptByIds(Integer[] ids);

    int update(DeptEntity dept);

    int insert(DeptEntity dept);


}