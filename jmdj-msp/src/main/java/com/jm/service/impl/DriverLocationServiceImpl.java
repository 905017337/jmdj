package com.jm.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.jm.service.DriverLocationService;
import com.jm.util.CoordinateTransform;
import lombok.val;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    @Override
    public ArrayList searchBefittingDriverAboutOrder(double startPlaceLatitude, double startPlaceLongitude, double endPlaceLatitude, double endPlaceLongitude, double mileage) {

        Point point = new Point(Convert.toDouble(startPlaceLongitude), Convert.toDouble(startPlaceLatitude));
        Metric metric = RedisGeoCommands.DistanceUnit.KILOMETERS;
        Distance distancecircle = new Distance(30, metric);
         Circle circle = new Circle(point, distancecircle);

        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance().includeCoordinates().sortAscending();
         GeoResults<RedisGeoCommands.GeoLocation<String>> radius = redisTemplate.opsForGeo().radius( "driver_location", circle,args);

        ArrayList<Object> arrayList = new ArrayList<>();
        if(radius != null){
            Iterator<GeoResult<RedisGeoCommands.GeoLocation<String>>> iterator = radius.iterator();
            while(iterator.hasNext()){
                GeoResult<RedisGeoCommands.GeoLocation<String>> result = iterator.next();
                RedisGeoCommands.GeoLocation<String> content = result.getContent();
                val driverId = content.getName();
                double dist = result.getDistance().getValue();
                if(!redisTemplate.hasKey("driver_online#"+driverId)){
                    continue;
                }
                Object obj = redisTemplate.opsForValue().get("driver_online#" + driverId);
                if(obj ==null){
                    continue;
                }
                final String value = obj.toString();
                final String[] temp = value.split("#");
                int rangeDistance = Integer.parseInt(temp[0]); //接单范围
                int orderDistance = Integer.parseInt(temp[1]);  //订单里程
                String orentation = temp[2];  //定向接单的点

                boolean bool_1 = (dist<=rangeDistance);  //dist 上车点和司机的距离
                boolean bool_2 = false;

                if(orderDistance ==0){
                    bool_2 = true;
                }else if(orderDistance == 5 && mileage >0 && mileage <= 5){
                    bool_2 = true;
                }else if(orderDistance == 10 && mileage >5 && mileage <= 10) {
                    bool_2 = true;
                }else if(orderDistance == 15 && mileage >10 && mileage <= 15) {
                    bool_2 = true;
                }else if(orderDistance == 30 && mileage >15 && mileage <= 30) {
                    bool_2 = true;
                }

                boolean bool_3 = false;
                if(!orentation.equals("none")){
                    double orientationLatitude = Double.parseDouble(orentation.split(",")[0]);
                    double orientationLongitude = Double.parseDouble(orentation.split(",")[1]);
                    //把定向点的火星坐标转换成GPS坐标
                    double[] location = CoordinateTransform.transformGCJ02ToWGS84(orientationLongitude, orientationLatitude);
                    final GlobalCoordinates point_1 = new GlobalCoordinates(location[1], location[0]);
                    //把订单终点的火星左边转换成GPS坐标
                    location = CoordinateTransform.transformGCJ02ToWGS84(endPlaceLongitude,endPlaceLatitude);
                    final GlobalCoordinates point_2 = new GlobalCoordinates(location[1], location[0]);
                    //这里不需要redis的GEO计算，直接用封装的函数计算两个GPS坐标之间的距离
                    GeodeticCurve geoCurve = new GeodeticCalculator()
                            .calculateGeodeticCurve(Ellipsoid.WGS84,point_1,point_2);

                    if(geoCurve.getEllipsoidalDistance() <=3000){
                        bool_3 = true;
                    }
                }else {
                    bool_3 = true;
                }

                if(bool_1 && bool_2 && bool_3){
                    HashMap map = new HashMap(){{
                        put("driverId",driverId);
                        put("distance",dist);
                    }};
                    arrayList.add(map);
                }

            }
        }
        return arrayList;
    }

    @Override
    public void updateOrderLocationCache(Map param) {
        long orderId = MapUtil.getLong(param, "orderId");
        String latitude = MapUtil.getStr(param, "latitude");
        String longitude = MapUtil.getStr(param, "longitude");
        String location=latitude+"#"+longitude;
        redisTemplate.opsForValue().set("order_location#"+orderId,location,10,TimeUnit.MINUTES);
    }

    @Override
    public HashMap searchOrderLocationCache(Long orderId) {
        Object obj = redisTemplate.opsForValue().get("order_location#" + orderId);
        if(obj!=null){
            String[] temp = obj.toString().split("#");
            String latitude = temp[0];
            String longitude = temp[1];
            HashMap map=new HashMap(){{
                put("latitude",latitude);
                put("longitude",longitude);
            }};
            return map;
        }
        return null;
    }
}