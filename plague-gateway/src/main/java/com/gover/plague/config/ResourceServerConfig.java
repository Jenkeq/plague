package com.gover.plague.config;

import com.gover.plague.handler.RestAccessDeniedHandler;
import com.gover.plague.handler.RestAuthenticationEntryPoint;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    // 权限校验类
    private final AuthorizationManager authorizationManager;
    // 白名单
    private final IgnoreUrlsConfig ignoreUrlsConfig;
    // 未授权处理器
    private final RestAccessDeniedHandler restAccessDeniedHandler;
    // 未认证处理器
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // Http 相关配置
        http
            .formLogin()
            .and()
//            .authorizeExchange()
//            // 白名单
//            .pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class)).permitAll()
//            // 权限管理
//            .anyExchange().access(authorizationManager)
//            // 异常处理
//            .and().exceptionHandling()
//            // 未授权
//            .accessDeniedHandler(restAccessDeniedHandler)
//            // 未认证
//            .authenticationEntryPoint(restAuthenticationEntryPoint)
//            .and()
            .csrf().disable()
            .httpBasic(withDefaults());
        return http.build();
    }
}
