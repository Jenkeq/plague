package com.gover.plague.config;

import com.gover.plague.service.LoginUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

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
    // 密码类
    private final PasswordEncoder passwordEncoder;
    // 认证管理器
    private final AuthenticationManager authenticationManager;
    // 登录用户服务类
    private final LoginUserDetailService userDetailsService;
    // JWToken增强类
    private final JWTokenEnhancer JWTokenEnhancer;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    // 客户端ID和客户端密钥
    private final static String client_id = "plague-app";
    private final static String client_secret = "B8ISFAHAN08HF7NF80WHF008FB0ABF";

    // JWT文件项目根目录下（resource目录）
    private final static String JWT_KEY_FILE_PATH = "jwt.jks";
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
                .userDetailsService(userDetailsService) //配置从数据库加载用户信息的服务
                .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(enhancerChain);
    }

    /**
     * 作用：
     * 主要是让/oauth/token支持client_id和client_secret做登陆认证如果开启了allowFormAuthenticationForClients，那么就在BasicAuthenticationFilter之前
     * 添加ClientCredentialsTokenEndpointFilter,使用ClientDetailsUserDetailsService来进行登陆认证
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    /**
     * JwtAccessTokenConverter：TokenEnhancer的子类，帮助程序在JWT编码的令牌值和OAuth身份验证信息之间进行转换（在两个方向上），
     * 同时充当TokenEnhancer授予令牌的时间。
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    /**
     * 生成jwt.jks：
     * 在JDK的bin目录下，执行 keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks，过程中所填密钥必需和 Client_secret 相同
     * @return
     */
    @Bean
    public KeyPair keyPair() {
        //从classpath下的证书中获取KeyPair
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(JWT_KEY_FILE_PATH), client_secret.toCharArray());
        return keyStoreKeyFactory.getKeyPair(JWT_KEY_FILE_ALIAS, client_secret.toCharArray());
    }

    /**
     * 在配置文件添加以下配置, 并添加依赖 <dependency>
     *             <groupId>org.springframework.boot</groupId>
     *             <artifactId>spring-boot-starter-data-redis</artifactId>
     *         </dependency>
     * spring:
     *   redis:
     *     host: 127.0.0.1
     *     port: 6379
     *     password: root
     *     database: 13
     * @return
     */
//    @Bean
//    public TokenStore tokenStore() {
//        //使用redis存储token，TokenStore可以自定义实现
//        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
//        //设置redis token存储中的前缀
//        redisTokenStore.setPrefix("auth-token:");
//        return redisTokenStore;
//    }
}
