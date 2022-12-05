package com.jm.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@Schema(description = "查询客户车辆的表单")
public class SearchCustomerCarListForm {
    @Min(value = 1, message = "customerId不能小于1")
    @Schema(description = "客户ID")
    private Long customerId;
}
