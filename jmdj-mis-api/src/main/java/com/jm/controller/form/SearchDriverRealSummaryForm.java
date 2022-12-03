package com.jm.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 19:52
 */
@Data
@Schema(description = "查询司机")
public class SearchDriverRealSummaryForm {

    @NotNull(message = "driverId不能为空")
    @Min(value = 1,message = "driverId不能小于1")
    @Schema(description = "司机ID")
    private Long driverId;

}