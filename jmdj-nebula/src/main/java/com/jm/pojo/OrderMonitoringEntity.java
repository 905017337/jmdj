package com.jm.pojo;

import lombok.Data;

@Data
public class OrderMonitoringEntity {
    private Long id;
    private Long orderId;
    private Byte status;
    private Integer records;
    private String safety;
    private Integer reviews;
    private Byte alarm;
    private String createTime;
}
