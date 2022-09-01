package com.gover.plague.service;

import com.gover.plague.cache.service.RedisService;
import com.gover.plague.constant.RedisConstant;
import com.gover.plague.user.service.UserLoginService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author gjk
 * @date 2022/08/30
 * @desc 将资源(URI) 与角色的映射关系放入Redis，由gateway模块取出校验
 */
@Service
public class Resource2AuthService {

    @Reference
    private RedisService redisService;

    @Reference
    private UserLoginService userLoginService;

    private Map<String, List<String>> resourceRolesMap;

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();

        // TODO URI路径资源对应的权限应该由数据库表加载
        resourceRolesMap.put("/api/order/v1/place", Arrays.asList("ADMIN", "DEV"));
        resourceRolesMap.put("/api/user/hello", Arrays.asList("ADMIN", "DEV"));
        resourceRolesMap.put("/api/warehouse/check/v1/queryStock", Arrays.asList("ADMIN", "DEV"));
        resourceRolesMap.put("/api/order/place/v1/find", Arrays.asList("ADMIN", "DEV"));

        // 在gateway模块会将这个Map取出进行验证
        redisService.hSetAll(RedisConstant.REDIS_WHITELIST_LIST_KEY, resourceRolesMap);
    }
}
