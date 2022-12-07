package com.jm.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.jm.service.DriverLocationService;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/7 20:57
 */
@Service
public class DriverLocationServiceImpl implements DriverLocationService {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void updateLocationCache(Map param) {
        Long driverId = MapUtil.getLong(param, "driverId");
        String latitude = MapUtil.getStr(param, "latitude");
        String longitude = MapUtil.getStr(param, "longitude");

        //接单范围
        Integer rangeDistance = MapUtil.getInt(param, "rangeDistance");
        //订单历程范围
        Integer orderDistance = MapUtil.getInt(param, "orderDistance");
        //封装成Point对象才能缓存到Redis里面
        Point point = new Point(Convert.toDouble(longitude), Convert.toDouble(latitude));
        /**
         * 把司机实时定位缓存到redis里面，便于Geo定位计算
         * Geo是集合形式，如果设置过期时间，所有司机的定位缓存就会全部失效了
         * 正确的做法是司机上线后，更新GEO中的定位缓存
         */
        redisTemplate.opsForGeo().add("driver_location",point,driverId+"");

        //定向接单地址的经度
        String orientateLongitude = null;
        if(param.get("orientateLongitude")!= null){
            orientateLongitude = MapUtil.getStr(param,"orientateLongitude");
        }

        //定向接单地址的纬度
        String orientateLatitude = null;
        if(param.get("orientateLatitude") != null){
            orientateLatitude = MapUtil.getStr(param,"orientateLatitude");
        }
        //定向接单的经纬度的字符串
        String orientation = "none";
        if(orientateLatitude!=null && orientateLongitude!=null){
            orientation = orientateLatitude+","+orientateLongitude;
        }

        /**
         * 为了解决判断哪些司机在线，我们还要单独弄一个上线缓存
         * 缓存司机的接单设置（定向接单，接单范围，订单总里程），便于系统判断该司机是否符合接单条件
         */
        String temp = rangeDistance + "#" +orderDistance +"#"+orientation;
        redisTemplate.opsForValue().set("driver_online#" +driverId,temp,60, TimeUnit.SECONDS);
    }

    @Override
    public void removeLocationCache(long driverId) {
        //删除司机定位缓存
        redisTemplate.opsForGeo().remove("driver_location",driverId+"");
        //删除司机上线缓存
        redisTemplate.delete("driver_online#"+driverId);
    }
}