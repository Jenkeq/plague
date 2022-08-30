package com.gover.plague.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gjk
 * @date 2022/08/30
 * @desc JWT增强类，将用户信息放入JWT
 */
@Component
public class JWTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 把登录用户LoginUser 的相关数据设置到JWT中, 方便后续取出, 这是无状态实现
        Map<String, Object> info = new HashMap<>();
        info.put("username", loginUser.getUsername());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }

}
