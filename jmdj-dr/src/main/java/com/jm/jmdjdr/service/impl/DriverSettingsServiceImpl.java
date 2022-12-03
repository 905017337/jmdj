package com.jm.jmdjdr.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.jm.jmdjdr.mapper.DriverSettingsDao;
import com.jm.jmdjdr.service.DriverSettingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/2 13:48
 */
@Service
public class DriverSettingsServiceImpl implements DriverSettingsService {

    @Resource
    private DriverSettingsDao settingsDao;

    @Override
    public HashMap searchDriverSettings(long driverId) {
        String settings = settingsDao.searchDriverSettings(driverId);
        HashMap map = JSONUtil.parseObj(settings).toBean(HashMap.class);
        boolean bool = MapUtil.getInt(map, "listenService") == 1?true:false;
        map.replace("listenService",bool);
        bool = MapUtil.getInt(map,"autoAccept") == 1?true:false;
        map.replace("authAccept",bool);
        return map;
    }
}