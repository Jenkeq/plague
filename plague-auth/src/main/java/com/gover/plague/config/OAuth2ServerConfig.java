package com.gover.plague.config;

import com.gover.plague.service.LoginUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gjk
 * @desc 认证服务器 (密码模式)，一切配置从这里开始
 * @date 2022/04/27
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final LoginUserDetailService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JWTokenEnhancer JWTokenEnhancer;

    private final static String client_id = "plague-app";
    private final static String client_secret = "B8ISFAHAN08HF7NF80WHF008FB0ABF";

    // JWT文件项目根目录下（resource目录）
    private final static String JWT_KEY_FILEPATH = "jwt.jks";
    private final static String JWT_KEY_FILE_ALIAS = "jwt";

    // 令牌和刷新令牌有效时间
    private final static int Access_Token_ValiditySeconds = 3600;
    private final static int Refresh_Token_ValiditySeconds = 86400;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(client_id)
                .secret(passwordEncoder.encode(client_secret))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                .accessTokenValiditySeconds(Access_Token_ValiditySeconds)
                .refreshTokenValiditySeconds(Refresh_Token_ValiditySeconds);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        //配置JWT的内容增强器
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(JWTokenEnhancer);
        delegates.add(accessTokenConverter());
        enhancerChain.setTokenEnhancers(delegates);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService) //配置加载用户信息的服务
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    /**
     * 生成jwt.jks：
     * 在JDK的bin目录下，执行 keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks，所填密钥必需和 Client_secret 相同
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(JWT_KEY_FILEPATH), client_secret.toCharArray());
        return keyStoreKeyFactory.getKeyPair(JWT_KEY_FILE_ALIAS, client_secret.toCharArray());
    }

}
