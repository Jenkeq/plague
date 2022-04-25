package com.gover.plague.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * # key-resolver ：配置文件使用 SpEL 表达式 #{@beanName} 从 Spring 容器中获取 KeyResolver 对象
 */
@Configuration
public class KeyResolverConfig {

    /**
     * 根据请求的主机的IP地址限流
     * @return
     */
    @Bean(name = "IpKeyResolver")
    @Primary
    KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    /**
     * 根据请求的Path 限流
     * @return
     */
    @Bean(name = "PathKeyResolver")
    KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

    /**
     * 根据用户ID 限流，请求路径中必须携带userId 参数
     * @return
     */
    @Bean(name = "UserIdKeyResolver")
    KeyResolver userIdKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }
}
