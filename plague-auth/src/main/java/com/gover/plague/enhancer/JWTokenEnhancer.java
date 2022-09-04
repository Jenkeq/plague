package com.gover.plague.enhancer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gover.plague.dto.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * token信息的额外信息处理, 可以在token里放你想要信息
 */
@Component
public class JWTokenEnhancer implements TokenEnhancer {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     *
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String,Object> additionalInfo = new HashMap<>();
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        try {
            String s = objectMapper.writeValueAsString(securityUser);
            Map<String,Object> map = objectMapper.readValue(s, new TypeReference<Map<String,Object>>(){});
            map.remove("password");
            map.remove("authorities");
            map.remove("accountNonExpired");
            map.remove("accountNonLocked");
            map.remove("credentialsNonExpired");
            map.remove("enabled");
            map.put("tokenType",accessToken.getTokenType());

            additionalInfo.put("userInfo" ,map);
            ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }
}
