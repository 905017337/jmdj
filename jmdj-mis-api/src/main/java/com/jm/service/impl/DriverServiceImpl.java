package com.jm.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.db.Page;
import com.jm.common.util.CosUtil;
import com.jm.common.util.PageUtils;
import com.jm.common.util.R;
import com.jm.controller.form.SearchDriverByPageForm;
import com.jm.controller.form.SearchDriverRealSummaryForm;
import com.jm.feign.DrServiceApi;
import com.jm.service.DriverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/3 12:30
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;
    @Resource
    private CosUtil cosUtil;
    @Override
    public PageUtils searchDriverByPage(SearchDriverByPageForm form) {
        R r = drServiceApi.searchDriverByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }

    @Override
    public HashMap searchDriverComprehensiveData(byte realAuth, Long driverId) {
        HashMap map = new HashMap<>();
        if(realAuth == 2 || realAuth == 3){
            SearchDriverRealSummaryForm form_1 = new SearchDriverRealSummaryForm();
            form_1.setDriverId(driverId);
            R r = drServiceApi.searchDriverRealSummary(form_1);
            HashMap summaryMap = (HashMap)r.get("result");

            //获取私有读写文件的临时url地址
            //获取私有读写文件的临时URL地址
            String idcardFront = MapUtil.getStr(summaryMap, "idcardFront");
            String idcardBack = MapUtil.getStr(summaryMap, "idcardBack");
            String idcardHolding = MapUtil.getStr(summaryMap, "idcardHolding");
            String drcardFront = MapUtil.getStr(summaryMap, "drcardFront");
            String drcardBack = MapUtil.getStr(summaryMap, "drcardBack");
            String drcardHolding = MapUtil.getStr(summaryMap, "drcardHolding");
            idcardFront = idcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(idcardFront):"";
            idcardBack = idcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(idcardBack):"";
            idcardHolding = idcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(idcardHolding):"";

            drcardFront = drcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(drcardFront):"";
            drcardBack = drcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(drcardBack):"";
            drcardHolding = drcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(drcardHolding):"";
            summaryMap.put("idcardFront", idcardFront);
            summaryMap.put("idcardBack", idcardBack);
            summaryMap.put("idcardHolding", idcardHolding);
            summaryMap.put("drcardFront", drcardFront);
            summaryMap.put("drcardBack", drcardBack);
            summaryMap.put("drcardHolding", drcardHolding);
            map.put("summaryMap",summaryMap);

            //TODO
        }
        return map;
    }
}