package com.gover.plague.user.service;

import com.gover.plague.common.ResultVO;
import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.ResRoleResp;
import com.gover.plague.user.resp.UserLoginResp;

import java.util.List;

/**
 * 用户登录接口
 */
public interface UserLoginService {

    UserLoginResp queryUserByName(UserLoginReq req);

    List<ResRoleResp> getAllResRole();

    UserLoginResp login(UserLoginReq user);
}
