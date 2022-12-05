package com.jm.controller;


import cn.hutool.core.bean.BeanUtil;

import com.jm.common.util.R;
import com.jm.controller.form.DeleteCustomerCarByIdForm;
import com.jm.controller.form.InsertCustomerCarForm;
import com.jm.controller.form.SearchCustomerCarListForm;
import com.jm.pojo.CustomerCarEntity;
import com.jm.service.CustomerCarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController()
@RequestMapping("/customer/car")
@Tag(name = "CustomerCarController", description = "客户车辆Web接口")
public class CustomerCarController {

    @Resource
    private CustomerCarService customerCarService;


    @PostMapping("/insertCustomerCar")
    @Operation(summary = "添加客户车辆")
    public R insertCustomerCar(@RequestBody @Valid InsertCustomerCarForm form) {
        CustomerCarEntity entity = BeanUtil.toBean(form, CustomerCarEntity.class);
        customerCarService.insertCustomerCar(entity);
        return R.ok();
    }

    @PostMapping("/searchCustomerCarList")
    @Operation(summary = "查询客户车辆列表")
    public R searchCustomerCarList(@RequestBody @Valid SearchCustomerCarListForm form) {
        ArrayList<HashMap> list = customerCarService.searchCustomerCarList(form.getCustomerId());
        return R.ok().put("result", list);
    }

    @PostMapping("/deleteCustomerCarById")
    @Operation(summary = "删除客户车辆")
    public R deleteCustomerCarById(@RequestBody @Valid DeleteCustomerCarByIdForm form) {
        int rows = customerCarService.deleteCustomerCarById(form.getId());
        return R.ok().put("rows", rows);
    }
}
