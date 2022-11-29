package com.jm.bffdriver.controller.form;

import lombok.Data;

/**
 * 删除腾讯云cos的文件表单
 * @author caozhenhao
 * @date 2022/11/26 13:34
 * @version 1.0
 */
@Data
public class DeleteCosFileForm {


    private String[] paths;
}