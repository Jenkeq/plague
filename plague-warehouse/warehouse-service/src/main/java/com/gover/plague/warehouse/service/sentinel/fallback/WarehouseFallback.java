package com.gover.plague.warehouse.service.sentinel.fallback;

import com.gover.plague.warehouse.req.WareCheckReq;
import com.gover.plague.warehouse.resp.WareCheckResp;

/**
 * @author jenkeq
 * @date
 * @describtion warehouse模块服务类的异常处理类 (这里的异常需要抛出才能处理)
 */
public class WarehouseFallback {

    /**
     * queryStock 发生异常后返回
     * @param req 必需和原方法一样
     * @return 必须和原方法一样
     */
    public static WareCheckResp queryStockFallback(WareCheckReq req) {
        return new WareCheckResp(-2);
    }
}
