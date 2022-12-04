package com.jm.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.jm.common.util.PageUtils;
import com.jm.common.util.R;
import com.jm.controller.form.SearchDriverByPageForm;
import com.jm.controller.form.SearchDriverComprehensiveDataForm;
import com.jm.controller.form.UpdateDriverRealAuthForm;
import com.jm.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 12:32
 */
@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController",description = "司机管理web接口")
public class DriverController {

    @Resource
    private DriverService driverService;



    @PostMapping("/searchDriverByPage")
    @SaCheckPermission(value = {"ROOT","DRIVER:SELECT"},mode = SaMode.OR)
    @Operation(summary = "查询司机分页记录")
    public R test(@RequestBody @Valid SearchDriverByPageForm form){
        PageUtils result = driverService.searchDriverByPage(form);
        return R.ok().put("result",result);
    }

    @PostMapping("/searchDriverComprehensiveData")
    @SaCheckPermission(value = {"ROOT","DRIVER:SELECT"},mode = SaMode.OR)
    @Operation(summary = "查询司机综合数据")
    public R searchDriverComprehensiveData(@RequestBody @Valid SearchDriverComprehensiveDataForm form){
        HashMap map =  driverService.searchDriverComprehensiveData(form.getRealAuth(),form.getDriverId());
        return R.ok().put("result",map);
    }

    @PostMapping("/updateDriverRealAuth")
    @SaCheckPermission(value = {"ROOT","DRIVER:UPDATE"},mode = SaMode.OR)
    @Operation(summary = "更新司机的实名认证状态")
    public R updateDriverRealAuth(@RequestBody @Valid UpdateDriverRealAuthForm form){
        int rows = driverService.updateDriverRealAuth(form);
        return R.ok().put("rows",rows);
    }
}