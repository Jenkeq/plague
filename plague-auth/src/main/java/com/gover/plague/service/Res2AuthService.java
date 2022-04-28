package com.gover.plague.service;


import com.gover.plague.cache.service.RedisService;
import com.gover.plague.constant.RedisConstant;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * 资源与角色匹配关系管理业务类
 *
 */
@Service
public class Res2AuthService {

    @Reference
    private RedisService redisService;

    private Map<String, List<String>> resourceRolesMap;

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();

        // TODO URI路径资源对应的权限应该由数据库表加载
        resourceRolesMap.put("/api/user/hello", Arrays.asList("ADMIN", "DEV"));

        resourceRolesMap.put("/api/warehouse/check/v1/queryStock", Arrays.asList("ADMIN", "DEV"));
        resourceRolesMap.put("/api/order/place/v1/find", Arrays.asList("ADMIN", "DEV"));

        redisService.hSetAll(RedisConstant.REDIS_WHITELIST_LIST_KEY, resourceRolesMap);
    }
}
