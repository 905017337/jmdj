package com.jm.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.jm.common.util.PageUtils;
import com.jm.common.util.R;
import com.jm.controller.form.SearchDriverByPageForm;
import com.jm.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 12:32
 */
@RestController
@RequestMapping("/dirver")
@Tag(name = "DriverController",description = "司机管理web接口")
public class DrverController {

    @Resource
    private DriverService driverService;

    @PostMapping("/searchDriverByPage")
    @SaCheckPermission(value = {"ROOT","DRIVER:SELECT"},mode = SaMode.OR)
    @Operation(summary = "查询司机分页记录")
    public R searchDriverByPage(@RequestBody @Valid SearchDriverByPageForm form){
        PageUtils result = driverService.searchDriverByPage(form);
        return R.ok().put("result",result);
    }
}