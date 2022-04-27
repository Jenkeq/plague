package com.gover.plague.config.auth;

import com.gover.plague.user.req.UserLoginReq;
import com.gover.plague.user.resp.UserLoginResp;
import com.gover.plague.user.service.UserLoginService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 定制化 UserDetailsService，从数据库加载User
 */
@Configuration
public class CustomUserDetailService implements UserDetailsService {

    @Reference
    private UserLoginService userLoginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.setName(username);
        UserLoginResp userLoginResp = userLoginService.queryUserByName(userLoginReq);

        LoginUser loginUser = new LoginUser();
        loginUser.setUserName(userLoginResp.getName());
        loginUser.setPassword(passwordEncoder.encode(userLoginResp.getPassword()));

        // 返回
        return new User(username, loginUser.getPassword(), loginUser.isEnabled(),
                loginUser.isAccountNonExpired(), loginUser.isCredentialsNonExpired(),
                loginUser.isAccountNonLocked(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
