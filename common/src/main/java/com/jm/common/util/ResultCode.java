package com.jm.common.util;

/**
 * 枚举了一些常用API操作码
 */
public enum ResultCode implements IErrorCode {

    /**
     * 用户状态相关
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    USERSTATUS(402, "用户被冻结"),
    USEREXIST(403, "用户不存在"),
    /**
     * 权限相关
     */
    ROLE_EXIST(420,"用户权限已存在"),
    FORBIDDEN(421, "没有相关权限"),
    ROLE_NOT_EXIST(422, "权限不存在"),

    /**
     * 资源相关
     */
    PERMISSION_NOT_EXIST(440,"权限不存在"),
    PERMISSION_ROLE_EXIST(441,"资源权限存在"),
    /**
     * 通用操作
     */
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(501, "参数检验失败");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
