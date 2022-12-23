package com.jm.bffdriver.feign;

import com.jm.bffdriver.config.MultipartSupportConfig;
import com.jm.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/23 20:18
 */
@FeignClient(value = "jmdj-nebula",configuration = MultipartSupportConfig.class)  //configuration 允许传递文件
public interface NebulaServiceApi {

    @PostMapping(value = "/monitoring/uploadRecordFile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)  //consumes 允许传递普通变量参数
    R uploadRecordFIle(@RequestPart(value = "file") MultipartFile file,
                       @RequestPart(value = "name") String name,
                       @RequestPart(value = "text",required = false)String text);

}