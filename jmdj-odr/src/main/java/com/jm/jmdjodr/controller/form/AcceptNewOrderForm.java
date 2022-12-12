package com.jm.jmdjodr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/12 10:39
 */
@Data
@Schema(description = "司机接单的表单")
public class AcceptNewOrderForm {

    @NotNull(message = "driverID不能为空")
    @Min(value = 1,message = "driverId不能小于1")
    @Schema(description = "司机ID")
    private Long driverId;

    @NotNull(message = "orderId不能为空")
    @Min(value = 1,message = "orderId不能小于1")
    @Schema(description = "订单ID")
    private Long orderId;
}