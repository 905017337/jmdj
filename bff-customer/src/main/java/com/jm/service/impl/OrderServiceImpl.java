package com.jm.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import com.jm.common.util.R;
import com.jm.controller.form.CreateNewOrderForm;
import com.jm.controller.form.EstimateOrderChargeForm;
import com.jm.controller.form.EstimateOrderMileageAndMinuteForm;
import com.jm.feign.MpsServiceApi;
import com.jm.feign.OdrServiceApi;
import com.jm.feign.RuleServiceApi;
import com.jm.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/6 21:10
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OdrServiceApi odrServiceApi;

    @Resource
    private MpsServiceApi mpsServiceApi;

    @Resource
    private RuleServiceApi ruleServiceApi;


    @Override
    public int createNewOrder(CreateNewOrderForm form) {
        Long customerId = form.getCustomerId();
        String startPlace = form.getStartPlace();
        String startPlaceLatitude = form.getStartPlaceLatitude();
        String startPlaceLongitude = form.getStartPlaceLongitude();
        String endPlace = form.getEndPlace();
        String endPlaceLatitude = form.getEndPlaceLatitude();
        String endPlaceLongitude = form.getEndPlaceLongitude();
        String favourFee = form.getFavourFee();

        EstimateOrderMileageAndMinuteForm form1 = new EstimateOrderMileageAndMinuteForm();
        form1.setMode("driving");
        form1.setStartPlaceLatitude(startPlaceLatitude);
        form1.setStartPlaceLongitude(startPlaceLongitude);
        form1.setEndPlaceLatitude(endPlaceLatitude);
        form1.setEndPlaceLongitude(endPlaceLongitude);
        R r = mpsServiceApi.estimateOrderMileageAndMinute(form1);
        HashMap map = (HashMap) r.get("result");
        String mileage = MapUtil.getStr(map, "mileage");  //预估的历程
        Integer minute = MapUtil.getInt(map, "minute");  //预估的时间

        EstimateOrderChargeForm form2 = new EstimateOrderChargeForm();
        form2.setMileage(mileage);
        form2.setTime(new DateTime().toTimeStr());
        r = ruleServiceApi.estimateOrderCharge(form2);
        map = (HashMap) r.get("result");
        String amount = MapUtil.getStr(map, "amount");
        String chargeRuleId = MapUtil.getStr(map, "chargeRuleId");
        String baseMileage = MapUtil.getStr(map, "baseMileage");
        String baseMileagePrice = MapUtil.getStr(map, "baseMileagePrice");
        String exceedMileagePrice = MapUtil.getStr(map, "exceedMileagePrice");
        String baseMinute = MapUtil.getStr(map, "baseMinute");
        String exceedMinutePrice = MapUtil.getStr(map, "exceedMinutePrice");
        String baseReturnMileage = MapUtil.getStr(map, "baseReturnMileage");
        String exceedReturnPrice = MapUtil.getStr(map, "exceedReturnPrice");



        return 0;
    }
}