package com.gover.plague.service;

import com.gover.plague.dto.SecurityUser;
import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.UserLoginResp;
import com.gover.plague.user.service.UserLoginService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author gjk
 * @desc 用于从数据库表加载用户数据
 * @date 2022/09/03
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private UserLoginService userLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        UserLoginReq req = new UserLoginReq();
        req.setUserName(username);
        UserLoginResp userResp = userLoginService.queryUserByName(req);
        if(userResp == null){
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        // 转化为Spring Security用户
        SecurityUser user = new SecurityUser(userResp);
        user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("XXX")));
        // 初步检测用户
        if(!user.getEnabled()){
            throw new RuntimeException("用户状态异常!");
        }
        return user;
    }
}
