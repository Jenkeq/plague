package com.gover.plague.common;

public class ResultVO<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 结果数据
     */
    private T data;

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
