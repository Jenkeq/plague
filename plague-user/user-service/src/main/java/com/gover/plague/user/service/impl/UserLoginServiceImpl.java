package com.gover.plague.user.service.impl;

import com.gover.plague.cache.service.RedisService;
import com.gover.plague.entity.User;
import com.gover.plague.repository.UserRepository;
import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.UserLoginResp;
import com.gover.plague.user.service.UserLoginService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserRepository userRepository;

    @Reference
    private RedisService redisService;

    // 领域服务
    // 事件发布

    @Override
    public UserLoginResp queryUserByName(UserLoginReq req) {
        User user = userRepository.getUserByName(req.getUserName());

        return new UserLoginResp(user.getId(), user.getUserName(), user.getPassword());
    }

    @Override
    public UserLoginResp login(UserLoginReq user) {
        return new UserLoginResp();
    }

}