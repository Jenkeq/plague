package com.gover.plague.service;

import com.gover.plague.config.LoginUser;
import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.UserLoginResp;
import com.gover.plague.user.service.UserLoginService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 定制化 UserDetailsService，从数据库加载User
 */
@Service
public class LoginUserDetailService implements UserDetailsService {

    @Reference
    private UserLoginService userLoginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserLoginReq req = new UserLoginReq();
        req.setUserName(username);
        UserLoginResp user = userLoginService.queryUserByName(req);

        // 找不到用户
        if(user == null){
            throw new UsernameNotFoundException("用户不存在!");
        }

        // TODO 暂时写死ADMIN权限
        user.setRoles(Arrays.asList("ADMIN"));
        LoginUser loginUser = new LoginUser(user);

        if (!loginUser.isEnabled()) {
            throw new DisabledException("!loginUser.isEnabled()");
        } else if (!loginUser.isAccountNonLocked()) {
            throw new LockedException("!loginUser.isAccountNonLocked()");
        } else if (!loginUser.isAccountNonExpired()) {
            throw new AccountExpiredException("!loginUser.isAccountNonExpired()");
        } else if (!loginUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("!loginUser.isCredentialsNonExpired()");
        }

        // 返回
        return loginUser;
    }

}
