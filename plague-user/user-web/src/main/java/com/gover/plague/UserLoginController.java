package com.gover.plague;

import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.UserLoginResp;
import com.gover.plague.user.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @GetMapping("/hello")
    public String hello(){
        System.out.println("进入hello方法");
        return "world!";
    }

    @RequestMapping("/qryUserByName")
    public UserLoginResp qryUserByName(@RequestBody UserLoginReq req){
        return userLoginService.queryUserByName(req);
    }

    @PostMapping("/login")
    public UserLoginResp login(@RequestBody UserLoginReq user){
        return userLoginService.login(user);
    }
}
