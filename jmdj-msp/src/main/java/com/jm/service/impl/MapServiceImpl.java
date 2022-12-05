package com.jm.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jm.common.exception.HxdsException;
import com.jm.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/4 13:56
 */
@Service
@Slf4j
public class MapServiceImpl implements MapService {

    //预计路程的API地址
    private String distanceUrl="https://apis.map.qq.com/ws/distance/v1/matrix/";

    //规划行进路线的API地址
    private String directionUrl = "https://apis.map.qq.com/ws/direction/v1/driving/";

    @Value("${tencent.map.key}")
    private String key;


    @Override
    public HashMap estimateOrderMileageAndMinute(String mode, String startPlaceLatitude, String startPlaceLongitude,
                                                 String endPlaceLatitude, String endPlaceLongitude) {

        HttpRequest req = new HttpRequest(distanceUrl);
        req.form("mode",mode);
        req.form("from",startPlaceLatitude+","+startPlaceLongitude);
        req.form("to",endPlaceLatitude+","+endPlaceLongitude);
        req.form("key",key);
        HttpResponse resp = req.execute();
        JSONObject json = JSONUtil.parseObj(resp.body());
        Integer status = json.getInt("status");
        String message = json.getStr("message");
        System.out.println(message);
        if(status != 0){
            log.error(message);
            throw new HxdsException("预估历程异常"+message);
        }
        JSONArray rows = json.getJSONObject("result").getJSONArray("rows");
        JSONObject element = rows.get(0,JSONObject.class).getJSONArray("elements").get(0,JSONObject.class);
        Integer distance = element.getInt("distance");
        String minleage = new BigDecimal(distance).divide(new BigDecimal(1000)).toString();
        Integer duration = element.getInt("duration");
        String  temp = new BigDecimal(duration).divide(new BigDecimal(60), 0, RoundingMode.CEILING).toString();
        int minute = Integer.parseInt(temp);
        HashMap map = new HashMap() {{
            put("mileage", minleage);
            put("minute", minute);
        }};
        return map;
    }

    @Override
    public HashMap calculateDriverLine(String startPlaceLatitude, String startPlaceLongitude,
                                       String endPlaceLatitude, String endPlaceLongitude) {

        HttpRequest req = new HttpRequest(directionUrl);
        req.form("from",startPlaceLatitude+","+startPlaceLongitude);
        req.form("to",endPlaceLatitude+","+endPlaceLongitude);
        req.form("key",key);

        HttpResponse resp = req.execute();
        JSONObject json = JSONUtil.parseObj(resp.body());
        Integer status = json.getInt("status");
        if(status != 0){
            throw  new HxdsException("执行异常");
        };
        JSONObject result = json.getJSONObject("result");
        HashMap map = result.toBean(HashMap.class);
        return map;
    }
}