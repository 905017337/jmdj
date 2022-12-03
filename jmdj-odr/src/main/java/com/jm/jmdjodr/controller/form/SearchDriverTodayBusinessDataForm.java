package com.jm.jmdjodr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/1 20:14
 */
@Data
@Schema(description = "查询司机当天营业数据的表单")
public class SearchDriverTodayBusinessDataForm {

    @NotNull(message = "dirverId不能为空")
    @Min(value = 1,message = "dirverId不能小于1")
    @Schema(description = "司机ID")
    private Long driverId;
}