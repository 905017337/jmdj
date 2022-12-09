package com.jm.pojo;

import jdk.jfr.DataAmount;
import lombok.Data;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2022/12/8 18:31
 */
@Data
public class NewOrderMessage {

    private String userId;
    private String orderId;
    private String from;
    private String to;
    private String expectsFee;
    private String mileage;
    private String minute;
    private String distance;
    private String favourFee;
}