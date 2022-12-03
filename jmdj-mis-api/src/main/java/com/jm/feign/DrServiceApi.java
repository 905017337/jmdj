package com.jm.feign;

import com.jm.common.util.R;
import com.jm.controller.form.SearchDriverByPageForm;
import com.jm.controller.form.SearchDriverRealSummaryForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 12:28
 */
@FeignClient(value = "jmdj-dr")
public interface DrServiceApi {

    @PostMapping("/driver/searchDriverByPage")
    R searchDriverByPage(SearchDriverByPageForm form);

    @PostMapping("/driver/searchDriverRealSummary")
    R searchDriverRealSummary(SearchDriverRealSummaryForm form);
}