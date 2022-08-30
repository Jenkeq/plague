package com.gover.plague.config;

import cn.hutool.core.convert.Convert;
import com.gover.plague.cache.service.RedisService;
import com.gover.plague.constant.AuthConstant;
import com.gover.plague.constant.RedisConstant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jenkeq
 * @date 2022/08/30
 * @desc 权限校验（包含访问资源和角色的映射校验）
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Reference
    private RedisService redisService;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        // 将Redis中的key为REDIS_WHITELIST_LIST_KEY的值取出，这是一个Map，将这个Map中key为uri.getPath()的值取出，这是应该是一个List<String>类型的Role
        Object obj = redisService.hGet(RedisConstant.REDIS_WHITELIST_LIST_KEY, uri.getPath());
        List<String> authorities = Convert.toList(String.class, obj);
        // 将每个Role加上AuthConstant.AUTHORITY_PREFIX 前缀
        authorities = authorities.stream().map(e -> e = AuthConstant.AUTHORITY_PREFIX + e).collect(Collectors.toList());

        // 认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                // 检测authorities中是否包含当前用户的Role，如果包含则放行，即完成了当前用户的角色是否可以访问uri.getPath()的校验
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}
