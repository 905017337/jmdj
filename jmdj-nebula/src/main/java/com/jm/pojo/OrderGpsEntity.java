package com.jm.pojo;

import lombok.Data;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/23 12:42
 */
@Data
public class OrderGpsEntity {

    private Long id;
    private Long orderId;
    private Long driverId;
    private Long customerId;
    private String latitude;
    private String longitude;
    private String speed;
    private String createTime;
}