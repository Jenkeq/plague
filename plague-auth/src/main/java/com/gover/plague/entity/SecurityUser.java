package com.gover.plague.entity;

import com.gover.plague.user.resp.UserLoginResp;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 登录用户类（技术上的用户类），它是一个 UserDetails
 */
@Data
@NoArgsConstructor
public class SecurityUser implements UserDetails {
    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 电话
     */
    private String phone;
    /**
     * 用户角色列表
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser(UserLoginResp user) {
        this.setId(Long.parseLong(user.getId()));
        this.setUsername(user.getUserName());
        this.setPassword(user.getPassword());
        this.setEnabled(Integer.parseInt(user.getStatus()) == 1);
        // 用户角色
        if (user.getRoles() != null) {
            authorities = new ArrayList<>();
            user.getRoles().forEach(item -> authorities.add(new SimpleGrantedAuthority(item)));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
