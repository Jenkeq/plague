package com.gover.plague.config;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.*;

@Configuration
public class SentinelGatewayConfig {

    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public SentinelGatewayConfig(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                 ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    /**
     * 为Spring Cloud Gateway 注册 block exception handler：SentinelGatewayBlockExceptionHandler
     * @return
     */
    @Bean
    @Order(-1)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * 限流过滤器, 默认顺序是HIGHEST_PRECEDENCE
     */
    @Bean
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    // --------------------------------- // --------------------------------- // ---------------------------------
    /**
     * GatewayFlowRule 网关限流规则，配置初始化的限流参数
     */
    @PostConstruct
    public void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();

        // 方式一.全局限流(资源限流)
//        rules.add(new GatewayFlowRule("plague-order") //资源名称
//                        .setCount(1) // 限流阈值
//                        .setIntervalSec(1) // 统计时间窗口，单位是秒，默认是 1 秒
//        );

        // 方式二.参数限流(资源限流)
//        rules.add(new GatewayFlowRule("plague-warehouse")
//                .setCount(1)
//                .setIntervalSec(1)
//                .setParamItem(new GatewayParamFlowItem()
//                        // 当请求URL路径中带有参数id时，会被匹配进限流
//                    .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM).setFieldName("id")
//                )
//        );

        // 方式三.通过自定义api分组进行限流，其这里的资源都在下面进行定义好了对应的匹配路径
        // 这里参数的含义是 IntervalSec秒内可以请求Count次
//        rules.add(new GatewayFlowRule("plague-order").setCount(1).setIntervalSec(1));
//        rules.add(new GatewayFlowRule("plague-warehouse").setCount(100).setIntervalSec(1));

        // 加载规则
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * 自定义API分组
     */
    @PostConstruct
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();

        // 订单API组
//        ApiDefinition api_order = new ApiDefinition("plague-order")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    // 路径匹配，也可以使用完整的URL路径
//                    add(new ApiPathPredicateItem().setPattern("/api/order/**").
//                            setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});

        // 仓库API组
//        ApiDefinition api_warehouse = new ApiDefinition("plague-warehouse")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    // 路径匹配，也可以使用完整的URL路径
//                    add(new ApiPathPredicateItem().setPattern("/api/warehouse/**").
//                            setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});

        // 添加API分组
//        definitions.add(api_order);
//        definitions.add(api_warehouse);

        // 设置
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * 自定义限流结果 (熔断)
     */
    @PostConstruct
    public void initBlockHandlers() {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                Map map = new HashMap<>();
                map.put("code", 600);
                map.put("message", "当前服务器繁忙, 请稍后再试!");
                // 限流后，可以返回内容也可以重定向
                return ServerResponse.status(HttpStatus.OK).
                        contentType(MediaType.APPLICATION_JSON_UTF8).
                        body(BodyInserters.fromObject(map));
            }
        };

        // 当请求阻塞时的处理器
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

}
