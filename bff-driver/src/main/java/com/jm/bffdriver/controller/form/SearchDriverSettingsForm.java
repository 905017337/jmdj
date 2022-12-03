package com.jm.bffdriver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/2 16:19
 */
@Data
@Schema(description = "查询司机设置的表单")
public class SearchDriverSettingsForm {

    @Schema(description = "司机ID")
    private Long driverId;
}