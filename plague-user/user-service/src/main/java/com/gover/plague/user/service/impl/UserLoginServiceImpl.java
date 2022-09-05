package com.gover.plague.user.service.impl;

import com.gover.plague.cache.service.RedisService;
import com.gover.plague.common.ResultVO;
import com.gover.plague.entity.ResRole;
import com.gover.plague.entity.User;
import com.gover.plague.repository.UserRepository;
import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.ResRoleResp;
import com.gover.plague.user.resp.UserLoginResp;
import com.gover.plague.user.service.UserLoginService;
import com.gover.plague.util.JWTUtil;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        return new UserLoginResp(user.getId(), user.getUserName(), user.getPassword(), user.getStatus(), null, user.getRoles());
    }

    @Override
    public List<ResRoleResp> getAllResRole() {
        List<ResRoleResp> respList = new ArrayList<>();
        List<ResRole> allResRole = userRepository.getAllResRole();
        for (ResRole resRole : allResRole) {
            respList.add(new ResRoleResp(resRole.getRoleName(), resRole.getUrl()));
        }
        return respList;
    }

    @Override
    public UserLoginResp login(UserLoginReq userReq) {
        UserLoginResp resp = new UserLoginResp();

        User user = userRepository.getUserByName(userReq.getUserName());
        // 校对密码, 数据库中存储的是 BCrypt 加密，盐值随机即可
        boolean checkResult = BCrypt.checkpw(userReq.getPassword(), user.getPassword());

        // 校对通过，签发token
        if (checkResult){
            long expireTime = 3600 * 1000;
            String token = JWTUtil.createJWT(UUID.randomUUID().toString(), user.getUserName(), expireTime);
            resp.setToken(token);
            resp.setUserName(user.getUserName());
            resp.setId(user.getId());
        }

        return resp;
    }

}