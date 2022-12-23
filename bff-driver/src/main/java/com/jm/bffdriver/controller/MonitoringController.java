package com.jm.bffdriver.controller;

import com.jm.bffdriver.feign.NebulaServiceApi;
import com.jm.common.exception.HxdsException;
import com.jm.common.util.R;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/23 20:23
 */
@RestController
@RequestMapping("/monitoring")
@Tag(name = "MonitoringController",description = "订单监控服务的web接口")
@Slf4j
public class MonitoringController {

    @Resource
    private NebulaServiceApi nebulaServiceApi;

    @PostMapping(value = "/uploadRecordFile")
    public R uploadRecordFile(@RequestPart("file")MultipartFile file,
                              @RequestPart("name")String name,
                              @RequestPart(value = "text",required = false)String text){
        if(file.isEmpty()){
            throw new HxdsException("上传文件不能为空");
        }
        nebulaServiceApi.uploadRecordFIle(file,name,text);
        return R.ok();
    }
}