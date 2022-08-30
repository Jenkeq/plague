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
 * @author gjk
 * @date 2022/08/30
 * @desc 从数据库加载User的相关信息，比如角色
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

        // TODO 暂时写死ADMIN权限，这个应该要从数据库表中获取
        user.setRoles(Arrays.asList("ADMIN"));
        LoginUser loginUser = new LoginUser(user);

        if (!loginUser.isEnabled()) {
            throw new DisabledException("用户未被启用");
        } else if (!loginUser.isAccountNonLocked()) {
            throw new LockedException("用户账户被锁定");
        } else if (!loginUser.isAccountNonExpired()) {
            throw new AccountExpiredException("用户账户已过期");
        } else if (!loginUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException("用户验证已过期");
        }

        // 返回
        return loginUser;
    }

}
