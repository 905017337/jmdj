package com.jm.jmdjdr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 13:21
 */
@Data
@Schema(description = "司机登录表单")
public class LoginForm {

    @NotBlank(message = "code不能为空")
    @Schema(description = "微信小程序临时授权")
    private String code;

    @NotBlank(message = "phoneCode不能为空")
    @Schema(description = "微信小程序获取电话号码临时授权")
    private String phoneCode;

}