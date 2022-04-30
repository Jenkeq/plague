package com.gover.plague.common;

import java.io.Serializable;

/**
 * 枚举常用API操作码
 */
public enum ResultCode implements IErrorCode, Serializable {
    SUCCESS(200, "操作成功!"),
    FAILED(500, "操作失败!"),
    VALIDATE_FAILED(404, "参数检验失败!"),
    UNAUTHORIZED(401, "未登录或token已过期!"),
    FORBIDDEN(403, "暂无相关权限!");
    private long code;
    private String message;

    ResultCode(long code, String message) {
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
