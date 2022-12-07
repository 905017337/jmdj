package com.jm.feign;

import com.jm.common.util.R;
import com.jm.controller.form.EstimateOrderChargeForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 20:58
 */
@FeignClient(value = "jmdj-rule")
public interface RuleServiceApi {

    @PostMapping("/charge/estimateOrderCharge")
R estimateOrderCharge(EstimateOrderChargeForm form);
}