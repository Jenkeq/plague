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
     * ???Spring Cloud Gateway ?????? block exception handler???SentinelGatewayBlockExceptionHandler
     * @return
     */
    @Bean
    @Order(-1)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    /**
     * ???????????????, ???????????????HIGHEST_PRECEDENCE
     */
    @Bean
    public GlobalFilter sentinelGatewayFilter() {
        return new SentinelGatewayFilter();
    }

    // --------------------------------- // --------------------------------- // ---------------------------------
    /**
     * GatewayFlowRule ???????????????????????????????????????????????????
     */
    @PostConstruct
    public void initGatewayRules() {
        Set<GatewayFlowRule> rules = new HashSet<>();

        // ?????????.????????????(????????????)
//        rules.add(new GatewayFlowRule("plague-order") //????????????
//                        .setCount(1) // ????????????
//                        .setIntervalSec(1) // ????????????????????????????????????????????? 1 ???
//        );

        // ?????????.????????????(????????????)
//        rules.add(new GatewayFlowRule("plague-warehouse")
//                .setCount(1)
//                .setIntervalSec(1)
//                .setParamItem(new GatewayParamFlowItem()
//                        // ?????????URL?????????????????????id???????????????????????????
//                    .setParseStrategy(SentinelGatewayConstants.PARAM_PARSE_STRATEGY_URL_PARAM).setFieldName("id")
//                )
//        );

        // ?????????.???????????????api??????????????????????????????????????????????????????????????????????????????????????????
        // ???????????????????????? IntervalSec??????????????????Count???
//        rules.add(new GatewayFlowRule("plague-order").setCount(1).setIntervalSec(1));
//        rules.add(new GatewayFlowRule("plague-warehouse").setCount(100).setIntervalSec(1));

        // ????????????
        GatewayRuleManager.loadRules(rules);
    }

    /**
     * ?????????API??????
     */
    @PostConstruct
    private void initCustomizedApis() {
        Set<ApiDefinition> definitions = new HashSet<>();

        // ??????API???
//        ApiDefinition api_order = new ApiDefinition("plague-order")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    // ???????????????????????????????????????URL??????
//                    add(new ApiPathPredicateItem().setPattern("/api/order/**").
//                            setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});

        // ??????API???
//        ApiDefinition api_warehouse = new ApiDefinition("plague-warehouse")
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    // ???????????????????????????????????????URL??????
//                    add(new ApiPathPredicateItem().setPattern("/api/warehouse/**").
//                            setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
//                }});

        // ??????API??????
//        definitions.add(api_order);
//        definitions.add(api_warehouse);

        // ??????
        GatewayApiDefinitionManager.loadApiDefinitions(definitions);
    }

    /**
     * ????????????????????? (??????)
     */
    @PostConstruct
    public void initBlockHandlers() {
        BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                Map map = new HashMap<>();
                map.put("code", 600);
                map.put("message", "?????????????????????, ???????????????!");
                // ????????????????????????????????????????????????
                return ServerResponse.status(HttpStatus.OK).
                        contentType(MediaType.APPLICATION_JSON_UTF8).
                        body(BodyInserters.fromObject(map));
            }
        };

        // ??????????????????????????????
        GatewayCallbackManager.setBlockHandler(blockRequestHandler);
    }

}
