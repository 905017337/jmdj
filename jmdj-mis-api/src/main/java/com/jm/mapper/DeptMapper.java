package com.jm.mapper;

import com.jm.pojo.DeptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 14:19
 */
@Mapper
public interface DeptMapper {

    ArrayList<HashMap> searchDeptByPage(Map param);

    long searchDeptCount(Map param);

    ArrayList<HashMap> searchAllDept();

    HashMap searchById(Integer id);

    boolean searchCanDelete(Integer[] ids);

    int deleteDeptByIds(Integer[] ids);

    int update(DeptEntity dept);

    int insert(DeptEntity dept);
}