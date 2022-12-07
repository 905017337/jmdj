package com.jm.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 19:49
 */
@Data
@Schema(description = "预估代驾费用的表单")
public class EstimateOrderChargeForm {

    @NotNull(message = "mileage不能为空")
    @Pattern(regexp = "^[1-9]\\d*\\.\\d+$|^0\\.\\d*[1-9]\\d*$|^[1-9]\\d*$",message = "mileage不能小于0")
    @Schema(description = "代驾公里数")
    private String mileage;

    @NotNull(message = "time不能为空")
    @Pattern(regexp = "^(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$",message = "time内容不正确")
    @Schema(description = "代驾开始时间")
    private String time;
}