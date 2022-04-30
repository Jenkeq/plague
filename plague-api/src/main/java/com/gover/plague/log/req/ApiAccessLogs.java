package com.gover.plague.log.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * API 访问日志
 */
@Data
public class ApiAccessLogs implements Serializable {
    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 操作时间
     */
    private Date startTime;

    /**
     * 消耗时间
     */
    private Integer spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 请求方法名
     */
    private String methodName;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 请求返回的结果
     */
    private Object result;

}
