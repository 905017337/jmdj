package com.jm.bffdriver.controller;

import com.jm.bffdriver.controller.form.DeleteCosFileForm;
import com.jm.common.util.CosUtil;
import com.jm.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @date 2022/11/26 13:37
 * @version 1.0
 */
@Slf4j
@RequestMapping("/cos")
@RestController
public class CosController {

    @Resource
    private CosUtil cosUtil;

    @PostMapping("/test")
    public R test(){
        return R.ok("====");
    }

    /**
     * 上传公有文件到系统
     * @param file
     * @param module
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadCosPublicFile")
    public R uploadCosPublicFile(@Param("file")MultipartFile file,@Param("module") String module) throws Exception {
        if(file.isEmpty()){
            throw  new Exception("上传文件不能为空");
        }
        try {
            String path = null;
            //TODO 此处应该有path路径赋值

            HashMap map = cosUtil.uploadPublicFile(file, path);
            return R.ok(map);
        }catch (IOException e){
            log.error("文件上传到腾讯云错误",e);
            throw new Exception("文件上传到腾讯云错误");
        }
    }

    /**
     * 上传私有文件到系统
     * @param file
     * @param module
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadCosPrivateFile")
    public R uploadCosprivateFile(@Param("file")MultipartFile file,@Param("module") String module) throws Exception {
        if(file.isEmpty()){
            throw  new Exception("上传文件不能为空");
        }
        try {
            String path = null;
            if (module.equals("driverAuth")){
                path="/driver/auth/";
            }else {
                throw new Exception("module错误");
            }

            HashMap map = cosUtil.uploadprivateFile(file, path);
            return R.ok(map);
        }catch (IOException e){
            log.error("文件上传到腾讯云错误",e);
            throw new Exception("文件上传到腾讯云错误");
        }
    }

    /**
     * 删除公有文件
     * @param form
     * @return
     */
    @PostMapping("/deleteCosPublicFile")
    public R deleteCosPublicFile(DeleteCosFileForm form){
        cosUtil.deletePublicFile(form.getPaths());
        return R.ok();
    }

    /**
     * 删除私有文件
     * @param form
     * @return
     */
    @PostMapping("/deleteCosPrivateFile")
    public R deleteCosPrivateFile(DeleteCosFileForm form){
        cosUtil.deletePrivateFile(form.getPaths());
        return R.ok();
    }
}