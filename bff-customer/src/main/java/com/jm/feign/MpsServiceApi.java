package com.jm.feign;

import com.jm.common.util.R;
import com.jm.controller.form.EstimateOrderMileageAndMinuteForm;
import com.jm.controller.form.SearchBefittingDriverAboutOrderForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/5 18:14
 */
@FeignClient(value = "jmdj-mps")
public interface MpsServiceApi {

    @PostMapping("/map/estimateOrderMileageAndMinute")
    R estimateOrderMileageAndMinute(EstimateOrderMileageAndMinuteForm form);

    @PostMapping("/driver/location/searchBefittingDriverAboutOrder")
    R searchBefittingDriverAboutOrder(SearchBefittingDriverAboutOrderForm form);

}