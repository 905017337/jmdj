package com.jm.jmdjdr.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author caozhenhao
 * @date 2022/11/28 18:18
 * @version 1.0
 */
@Data
public class WalletEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 司机ID
     */
    private Long driverId;

    /**
     * 钱包金额
     */
    private BigDecimal balance;

    /**
     * 支付密码
     */
    private String password;


}