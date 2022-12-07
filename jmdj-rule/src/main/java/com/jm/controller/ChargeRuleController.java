package com.jm.controller;

import com.jm.common.util.R;
import com.jm.controller.form.EstimateOrderChargeForm;
import com.jm.service.ChargeRuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 19:47
 */
@RestController
@RequestMapping("/charge")
@Tag(name = "ChargeRuleController",description = "代驾费用的Web接口")
public class ChargeRuleController {

    @Resource
    private ChargeRuleService chargeRuleService;

    @PostMapping("/estimateOrderCharge")
    public R estimateOrderCharge(@RequestBody @Validated EstimateOrderChargeForm form){
        HashMap map = chargeRuleService.calculateOrderCharge(form.getMileage(),form.getTime());
        return R.ok().put("result",map);
    }

}