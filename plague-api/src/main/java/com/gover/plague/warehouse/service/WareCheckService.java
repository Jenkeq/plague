package com.gover.plague.warehouse.service;

import com.gover.plague.warehouse.req.WareCheckReq;
import com.gover.plague.warehouse.resp.WareCheckResp;

public interface WareCheckService {
    /**
     * 根据订单ID查找订单
     * @param req
     * @return
     */
    WareCheckResp queryStock(WareCheckReq req);
}
