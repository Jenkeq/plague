package com.gover.plague.user.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginReq implements Serializable {
    // 登录方式（0用户名，1手机号码，2第三方平台（21 QQ 22微信 23Google））
    int loginType;
    // 用户名
    String userName;
    // 手机号码
    String phoneNo;
    // 密码
    String password;
}
