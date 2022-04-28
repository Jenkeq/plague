//package com.gover.plague.filter;
//
//import com.gover.plague.util.JWTUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
///**
// * @author jenkeq
// * @date
// * @describe token过滤器
// */
//public class TokenFilter implements GlobalFilter, Ordered {
//
//    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);
//
//    private static final String AUTHORIZE_TOKEN = "token";
//    private static final String LOGIN_PATH = "/api/user/login";
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        //1. 获取请求
//        ServerHttpRequest request = exchange.getRequest();
//        //2. 则获取响应
//        ServerHttpResponse response = exchange.getResponse();
//        //3. 如果是登录请求则放行
//        if (request.getURI().getPath().contains(LOGIN_PATH)) {
//            return chain.filter(exchange);
//        }
//        //4. 获取请求头
//        HttpHeaders headers = request.getHeaders();
//        //5. 请求头中获取令牌
//        String token = headers.getFirst(AUTHORIZE_TOKEN);
//
//        //6. token为空
//        if (token == null || token.isEmpty()) {
//            System.out.println("token为空!");
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        //7. 如果请求头中有令牌则解析令牌
//        try {
//            JWTUtil.parseJWT(token);
//        } catch (Exception e) {
//            System.out.println("解析Token出错！");
//            //8. 解析jwt令牌出错, 说明令牌过期或者伪造等不合法情况出现
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            //9. 返回
//            return response.setComplete();
//        }
//
//        //10. 放行
//        return chain.filter(exchange);
//    }
//
//    @Override
//    public int getOrder() {
//        return -100;
//    }
//}
