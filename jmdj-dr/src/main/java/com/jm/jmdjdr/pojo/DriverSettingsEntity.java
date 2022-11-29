package com.jm.jmdjdr.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author caozhenhao
 * @date 2022/11/28 18:15
 * @version 1.0
 */
@Data
public class DriverSettingsEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 司机ID
     */
    private Long driverId;

    /**
     * 个人设置
     */
    private String settings;

    private static final long serialVersionUID = 1L;
}