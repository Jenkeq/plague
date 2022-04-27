package com.gover.plague.user.service;

import com.gover.plague.common.ResultVO;
import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.UserLoginResp;

/**
 * 用户登录接口
 */
public interface UserLoginService {

    UserLoginResp queryUserByName(UserLoginReq req);

    UserLoginResp login(UserLoginReq user);
}
