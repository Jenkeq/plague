package com.gover.plague.common;

import java.io.Serializable;

/**
 * 封装API的错误码
 *
 */
public interface IErrorCode extends Serializable {
    int getCode();
    String getMsg();
}
