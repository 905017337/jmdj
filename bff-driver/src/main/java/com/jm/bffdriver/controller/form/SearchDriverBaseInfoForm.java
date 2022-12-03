package com.jm.bffdriver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 18:44
 */

@Data
@Schema(description = "查询司机基本信息的表单")
public class SearchDriverBaseInfoForm {

    @Schema(description = "司机ID")
    private Long driverId;

}