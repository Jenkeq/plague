package com.gover.plague;

import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.UserLoginResp;
import com.gover.plague.user.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    /**
     * 这个接口用户应该未经验证即可匿名登录
     */
    @RequestMapping("/qryUserByName")
    public UserLoginResp qryUserByName(@RequestBody UserLoginReq req){
        return userLoginService.queryUserByName(req);
    }

    @PostMapping("/login")
    public UserLoginResp login(@RequestBody UserLoginReq user){
        return userLoginService.login(user);
    }
}
