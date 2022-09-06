package com.gover.plague.common;

import java.io.Serializable;

/**
 * 枚举常用API操作码
 */
public enum ResultCode implements IErrorCode, Serializable {
    SUCCESS(1000, "操作成功"),
    FAILED(1001, "操作失败"),
    VALIDATE_FAILED(1002, "参数检验失败"),
    TOKENISM(1003, "Token缺失"),
    TOKENIZER(1004, "Token错误"),
    UNAUTHORIZED(1005, "未登录或Token已过期"),
    FORBIDDEN(1006, "当前用户无权访问"),
    INTER_ERROR(1007, "内部服务器错误"),
    NOTFOUND(1008, "访问资源未找到"),
    ILLEGAL_ACCESS(1009, "非法方式访问");

    private int code;
    private String msg;

    ResultCode(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
