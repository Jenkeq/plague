package com.gover.plague.config;

import com.gover.plague.enhancer.JWTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 认证服务配置适配器
 * 我们需要重写三个方法(不写就是用默认的, 根据需求,一般需要重写)
 *
 * @Override public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
 * 访问安全配置。
 * }
 * @Override public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
 * 决定clientDeatils信息的处理服务
 * }
 * @Override public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
 * 访问端点配置。tokenStroe、tokenEnhancer服务
 * }
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    /**
     * token生成策略
     */
    @Autowired
    private TokenStore tokenStore;
    /**
     * 授权码服务类
     */
    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;
    /**
     * 身份信息管理类
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("accessTokenConverter")
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    /**
     * token信息的额外信息处理
     */
    @Autowired
    private JWTokenEnhancer jwtokenEnhancer;

    @Autowired
    private DataSource dataSource;

    /**
     * clientDetail 信息里的client_secret字段加解密器
     * client_secret密码加密器
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置客户端信息
     * 配置从哪里获取ClientDetails信息
     * 在client_credentials授权方式下，只要这个ClientDetails信息。
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /**
         * 生产环境，将客户端信息存储在内存中
         */
        clients.jdbc(dataSource);
        /**
         * 测试环境，将客户端信息存储在内存中
         */
//        clients.inMemory()
//                .withClient("c1")   // client_id
//                .secret(passwordEncoder().encode("secret"))       // client_secret
//                .authorizedGrantTypes("authorization_code","password")     // 该client允许的授权类型
//                .scopes("all")     // 允许的授权范围
//                .autoApprove(true); //登录后绕过批准询问(/oauth/confirm_access)
    }

    /**
     * token 令牌服务
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setSupportRefreshToken(true); //支持refresh_token
        services.setTokenStore(tokenStore);//token的保存方式
        services.setTokenEnhancer(tokenEnhancer());//token里加点信息
        return services;
    }

    /**
     * token增强类
     * @return
     */
    public TokenEnhancerChain tokenEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtokenEnhancer, jwtAccessTokenConverter));
        return tokenEnhancerChain;
    }

    /**
     * 授权码服务类
     * @param dataSource
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 配置认证服务端点
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(authorizationCodeServices)
                .tokenServices(tokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
        /**
         * 替换原有的默认授权地址为新的授权地址
         */
//        endpoints.pathMapping("/oauth/token", "/oauth/login");
    }

    /**
     * 配置：安全检查流程,用来配置令牌端点（Token Endpoint）的安全与权限访问
     *
     * 默认过滤器：BasicAuthenticationFilter
     * 1、oauth_client_details表中clientSecret字段加密【ClientDetails属性secret】
     * 2、CheckEndpoint类的接口 oauth/check_token 无需经过过滤器过滤，默认值：denyAll()
     *
     * 对以下的几个端点进行权限配置：
     * /oauth/authorize：授权端点
     * /oauth/token：令牌端点
     * /oauth/confirm_access：用户确认授权提交端点
     * /oauth/error：授权服务错误信息端点
     * /oauth/check_token：用于资源服务访问的令牌解析端点
     * /oauth/token_key：提供公有密匙的端点，如果使用JWT令牌的话
     **/
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }
}
