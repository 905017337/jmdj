package com.jm.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/23 18:07
 */
public interface MonitoringService {

    void monitoring(MultipartFile file,String name,String text);

    int insertOrderMonitoring(long orderId);

}