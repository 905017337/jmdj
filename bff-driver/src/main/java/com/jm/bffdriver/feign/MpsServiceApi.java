package com.jm.bffdriver.feign;

import com.jm.bffdriver.controller.form.RemoveLocationCacheForm;
import com.jm.bffdriver.controller.form.UpdateLocationCacheForm;
import com.jm.bffdriver.controller.form.UpdateOrderLocationCacheForm;
import com.jm.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/8 09:37
 */
@FeignClient(value = "jmdj-mps")
public interface MpsServiceApi {
    @PostMapping("/driver/location/updateLocationCache")
    R updateLocationCache(UpdateLocationCacheForm form);

    @PostMapping("/driver/location/removeLocationCache")
    R removeLocationCache(RemoveLocationCacheForm form);


    @PostMapping("/driver/location/updateOrderLocationCache")
    void updateOrderLocationCache(UpdateOrderLocationCacheForm form);

}