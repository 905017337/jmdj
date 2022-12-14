package com.jm.bffdriver.feign;

import com.jm.bffdriver.controller.form.SearchCustomerInfoInOrderForm;
import com.jm.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/14 19:39
 */
@FeignClient("jmdj-cst")
public interface CstServiceApi {

    @PostMapping("/customer/searchCustomerInfoInOrder")
    R searchCustomerInfoInOrder(SearchCustomerInfoInOrderForm form);
}