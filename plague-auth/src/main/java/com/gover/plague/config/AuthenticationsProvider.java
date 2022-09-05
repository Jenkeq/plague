package com.gover.plague.config;

import com.gover.plague.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author gjk
 * @date 2022/09/05
 * @desc 暂时不用
 */
//@Component
@Deprecated
public class AuthenticationsProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailService userDetailsService;

    /**
     * 获取表单提交的信息
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object credentials = authentication.getCredentials();
        String name = authentication.getName();
        Object principal = authentication.getPrincipal();
        //获取用户信息
        UserDetails user = userDetailsService.loadUserByUsername(name);
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, principal, authorities);
    }

    /**
     * @Description 如果该AuthenticationProvider支持传入的Authentication对象，则返回true
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
