package com.gover.plague.filter;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gover.plague.cache.service.RedisService;
import com.gover.plague.common.ResultCode;
import com.gover.plague.config.IgnoreUrlsConfig;
import com.gover.plague.constant.AuthConstant;
import com.gover.plague.constant.RedisConstant;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.springframework.web.reactive.socket.CloseStatus.SERVER_ERROR;

/**
 * Token 验证
 */
@AllArgsConstructor
@Component
public class TokenFilter implements GlobalFilter, Ordered {

    @Reference
    private RedisService redisService;

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    private static final String SIGNING_KEY = AuthConstant.SIGNING_KEY;

    private static final String BEAR_HEADER = "Bearer ";

    private static final Gson gson = new Gson();

    /**
     * 该值要和auth-server中配置的签名相同
     * <p>
     * com.kdyzm.spring.security.auth.center.config.TokenConfig#SIGNING_KEY
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverRequest = exchange.getRequest();
        // 将Payload数据放到header
        ServerHttpRequest.Builder builder = serverRequest.mutate();
        // 取出URI
        String resUri = exchange.getRequest().getURI().getPath();
        // ======================= 第一步校验：验证token =======================
        Map<String, Object> userInfo = null;
        List<String> ignoreList = ignoreUrlsConfig.getUrls();
        if(ignoreList != null && !ignoreList.contains(resUri)){
            String token = serverRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            // 第一步，如果没有token,则直接返回错误
            if (StringUtils.isEmpty(token)) {
                return unAuthorized(exchange, ResultCode.TOKENISM.getMsg(), ResultCode.TOKENISM.getCode());
            }

            // 第二步，验证并获取PayLoad
            String payLoad;
            try {
                Jwt jwt = JwtHelper.decodeAndVerify(token.replace(BEAR_HEADER, ""), new MacSigner(SIGNING_KEY));
                payLoad = jwt.getClaims();
            } catch (Exception e) {
                return unAuthorized(exchange, ResultCode.TOKENIZER.getMsg(),ResultCode.TOKENIZER.getCode());
            }
            try {
                // 取出exp字段，判断token是否已经过期
                userInfo = objectMapper.readValue(payLoad, new TypeReference<Map<String, Object>>() {});
                long expiration = ((Integer) userInfo.get("exp")) * 1000L;
                if (expiration < System.currentTimeMillis()) {
                    return unAuthorized(exchange, ResultCode.UNAUTHORIZED.getMsg(),ResultCode.UNAUTHORIZED.getCode());
                }

                // ======================= 第二步校验：验证URL和Role映射关系是否与当前访问用户的Role匹配 =======================
                try {
                    // 将Redis中的key为REDIS_WHITELIST_LIST_KEY的值取出，这是一个Map，将这个Map中key为uri的值取出，值是一个List<String>类型的Role
                    Object obj = redisService.hGet(RedisConstant.REDIS_WHITELIST_LIST_KEY, resUri);
                    List<String> uriAuthList = Convert.toList(String.class, obj);
                    List<String> userAuthList = gson.fromJson(JSON.toJSONString(userInfo.get("authorities")), List.class);
                    // retainAll方法：如果 uriAuthList 中有 userAuthList 没有的数据, 则返回false
                    if (Collections.disjoint(uriAuthList, userAuthList == null ? Collections.EMPTY_LIST : userAuthList)) {
                        return unAuthorized(exchange, ResultCode.FORBIDDEN.getMsg(), ResultCode.FORBIDDEN.getCode());
                    }
                }catch (Exception e){
                    throw new RuntimeException("URL权限验证出错");
                }
            } catch (IOException e) {
                return unAuthorized(exchange, ResultCode.TOKENIZER.getMsg(),ResultCode.TOKENIZER.getCode());
            }
            // 无状态实现
            builder.header("token-info", Base64.encode(payLoad.getBytes(StandardCharsets.UTF_8))).build();
        }

        // 继续执行
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }

    /**
     * 封装返回数据
     */
    private Mono<Void> unAuthorized(ServerWebExchange exchange, String msg,Integer status) {
        try {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            //这里需要指定响应头部信息，否则会中文乱码
            exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            JSONObject ret=new JSONObject();
            if(Objects.isNull(status)) {
                ret.put("status", SERVER_ERROR);
                ret.put("info", msg);
            }else{
                ret.put("status", status);
                ret.put("info", msg);
            }
            String s = objectMapper.writeValueAsString(ret);
            DataBuffer buffer = exchange
                    .getResponse()
                    .bufferFactory()
                    .wrap(s.getBytes(StandardCharsets.UTF_8));
            return exchange.getResponse().writeWith(Flux.just(buffer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将该过滤器的优先级设置为最高，因为只要认证不通过，就不能做任何事情
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}