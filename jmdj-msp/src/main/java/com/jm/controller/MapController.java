package com.jm.controller;

import com.jm.common.util.R;
import com.jm.controller.form.CalculateDriverLineForm;
import com.jm.service.MapService;
import com.jm.controller.form.EstimateOrderMileageAndMinuteForm;
import io.swagger.v3.oas.annotations.Operation;
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
 * @date 2022/12/4 14:16
 */
@RestController
@RequestMapping("/map")
@Tag(name = "MapController",description = "地图webJ接口")
public class MapController {

    @Resource
    private MapService mapService;


    // 40.032747,116.544414
    // 39.95518,116.354905
    @PostMapping("/estimateOrderMileageAndMinute")
    @Operation(summary = "估算历程和时间")
    public R estimateOrderMileageAndMinute(@RequestBody @Validated EstimateOrderMileageAndMinuteForm form){
        HashMap map = mapService.estimateOrderMileageAndMinute(form.getMode(),
                form.getStartPlaceLatitude(), form.getStartPlaceLongitude(),
                form.getEndPlaceLatitude(), form.getStartPlaceLongitude());
        return R.ok().put("result",map);
    }

    @PostMapping("/calculateDriveLine")
    @Operation(summary = "计算行驶路线")
    public R calculateDriverLine(@RequestBody @Validated CalculateDriverLineForm form){
        HashMap map = mapService.calculateDriverLine(form.getStartPlaceLatitude(), form.getStartPlaceLongitude(),
                form.getEndPlaceLatitude(), form.getEndPlaceLongitude());
        return R.ok().put("result",map);
    }

}