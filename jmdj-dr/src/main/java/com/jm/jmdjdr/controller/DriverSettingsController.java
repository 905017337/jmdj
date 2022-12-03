package com.jm.jmdjdr.controller;

import com.jm.common.util.R;
import com.jm.jmdjdr.controller.form.SearchDriverBaseInfoForm;
import com.jm.jmdjdr.service.DriverSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/2 13:51
 */
@RestController
@RequestMapping("/settings")
@Tag(name = "SettingsController", description = "司机甚至模块web接口")
public class DriverSettingsController {

    @Resource
    private DriverSettingsService driverSettingsService;

    @PostMapping("/searchDriverSettings")
    @Operation(summary = "查询司机的设置")
    public R searchDriverSettings(@RequestBody @Valid SearchDriverBaseInfoForm form){
        HashMap map = driverSettingsService.searchDriverSettings(form.getDriverId());
        return R.ok().put("result",map);
    }

}