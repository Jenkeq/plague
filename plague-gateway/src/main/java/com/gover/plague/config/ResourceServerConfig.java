package com.gover.plague.config;

import cn.hutool.core.util.ArrayUtil;
import com.gover.plague.constant.AuthConstant;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author gjk
 * @date 2022/08/30 23:52
 * @desc 资源服务器(网关相当于是 OAuth 2.0中的资源服务器)，一切配置从这里开始
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    // 权限校验器
    private final AuthorizationManager authorizationManager;
    // 白名单
    private final IgnoreUrlsConfig ignoreUrlsConfig;
    // 处理未授权
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    // 处理未认证
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // JWT 相关配置
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
        // Http 相关配置
        http.authorizeExchange()
                //白名单
                .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class)).permitAll()
                //权限校验
                .anyExchange().access(authorizationManager)
                //异常处理
                .and().exceptionHandling()
                //未授权
                .accessDeniedHandler(restAccessDeniedHandler)
                //未认证
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                //禁用CSRF
                .and().csrf().disable();
        return http.build();
    }

    /**
     * 从JWT令牌中获取认证对象
     */
    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}
