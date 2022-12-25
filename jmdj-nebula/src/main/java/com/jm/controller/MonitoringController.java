package com.jm.controller;

import com.jm.common.exception.HxdsException;
import com.jm.common.util.R;
import com.jm.controller.form.InsertOrderMonitoringForm;
import com.jm.service.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/23 20:11
 */
@RestController
@RequestMapping("/monitoring")
@Tag(name = "MonitoringController",description = "监控服务的web接口")
@Slf4j
public class MonitoringController {

    @Resource
    private MonitoringService monitoringService;

    @PostMapping("/uploadRecordFile")
    @Operation(summary = "上传代驾录音文件")
    public R uploadRecordFile(@RequestPart("file") MultipartFile file,
                              @RequestPart("name") String name,
                              @RequestPart(value = "text",required = false) String text){
        if(file.isEmpty()){
            throw new HxdsException("录音文件不能为空");
        }
        monitoringService.monitoring(file,name,text);
        return R.ok();
    }

    @PostMapping("/insertOrderMonitoring")
    @Operation(summary = "添加订单监控记录")
    public R insertOrderMonitoring(@RequestBody @Valid InsertOrderMonitoringForm form){
        int rows = monitoringService.insertOrderMonitoring(form.getOrderId());
        return R.ok().put("rows",rows);
    }

}