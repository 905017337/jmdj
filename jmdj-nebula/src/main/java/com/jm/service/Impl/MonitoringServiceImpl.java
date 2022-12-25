package com.jm.service.Impl;

import cn.hutool.core.util.IdUtil;
import com.jm.common.exception.HxdsException;
import com.jm.mapper.OrderMonitoringMapper;
import com.jm.mapper.OrderVoiceTextMapper;
import com.jm.pojo.OrderVoiceTextEntity;
import com.jm.service.MonitoringService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/23 18:08
 */
@Service
@Slf4j
public class MonitoringServiceImpl implements MonitoringService {

    @Resource
    private OrderVoiceTextMapper orderVoiceTextMapper;

    @Resource
    private OrderMonitoringMapper orderMonitoringMapper;

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Override
    @Transactional
    public void monitoring(MultipartFile file, String name, String text) {
        //上传录音到minio
        try {
            MinioClient client = new MinioClient.Builder().endpoint(endpoint).credentials(accessKey,secretKey).build();
            client.putObject(
                    PutObjectArgs.builder().bucket("jmjd-record").object(name)
                            .stream(file.getInputStream(),-1,20971520)
                            .contentType("audio/x-mpeg")
                            .build());
        }catch (Exception e){
            log.error("上传录音文件失败",e);
            throw new HxdsException("上传录音文件失败");
        }

        OrderVoiceTextEntity entity = new OrderVoiceTextEntity();

        //文件明格式如：215635665617-1。名牌，我们要解析出订单号
        String[] temp = name.substring(0,name.indexOf(".mp3")).split("-");
        Long orderId = Long.parseLong(temp[0]);

        String uuid = IdUtil.simpleUUID();
        entity.setUuid(uuid);
        entity.setOrderId(orderId);
        entity.setRecordFile(name);
        entity.setText(text);
        //把文稿保存到HBase
        int rows=  orderVoiceTextMapper.insert(entity);
        if(rows != 1){
            throw new HxdsException("保存录音文稿失败");
        }
        //TODO 执行文稿内容审查


    }

    @Override
    @Transactional
    public int insertOrderMonitoring(long orderId) {
        int rows = orderMonitoringMapper.insert(orderId);
        if(rows != 1){
            throw new HxdsException("添加订单监控摘要记录失败");
        }
        return rows;
    }

}