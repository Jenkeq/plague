package com.gover.plague.warehouse.service.sentinel.block;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gover.plague.warehouse.req.WareCheckReq;
import com.gover.plague.warehouse.resp.WareCheckResp;

/**
 * @author jenkeq
 * @date
 * @describtion warehouse模块服务类的限流处理类
 */
public class WarehouseBlockHandler {

    /**
     * queryStock 限流后返回
     * @param req 必需和原方法一样
     * @param ex 必需
     * @return 必须和原方法一样
     */
    public static WareCheckResp queryStockBlock(WareCheckReq req, BlockException ex) {
        return new WareCheckResp(-1);
    }
}
