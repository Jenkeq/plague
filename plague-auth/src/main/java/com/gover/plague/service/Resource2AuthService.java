package com.gover.plague.service;

import com.gover.plague.cache.service.RedisService;
import com.gover.plague.constant.RedisConstant;
import com.gover.plague.user.resp.ResRoleResp;
import com.gover.plague.user.service.UserLoginService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

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
        // 将数据库表中的资源和角色映射关系放进去
        List<ResRoleResp> allResRole = userLoginService.getAllResRole();
        for (ResRoleResp resp : allResRole) {
            if(resourceRolesMap.containsKey(resp.getUrl())){
                resourceRolesMap.get(resp.getUrl()).add(resp.getRoleName());
            }
            else{
                List<String> urlList = new ArrayList<>();
                urlList.add(resp.getRoleName());
                resourceRolesMap.put(resp.getUrl(), urlList);
            }
        }

//        测试环境
//        resourceRolesMap.clear();
//        resourceRolesMap.put("/api/order/v1/place", Arrays.asList("ROLE_ADMIN", "ROLE_DEV"));
//        resourceRolesMap.put("/api/warehouse/check/v1/queryStock", Arrays.asList("ROLE_ADMIN"));
//        resourceRolesMap.put("/api/order/place/v1/find", Arrays.asList("ROLE_ADMIN"));

        // 在gateway模块会将这个Map取出进行验证
        redisService.hSetAll(RedisConstant.REDIS_RES2ROLE_KEY, resourceRolesMap);
    }
}
