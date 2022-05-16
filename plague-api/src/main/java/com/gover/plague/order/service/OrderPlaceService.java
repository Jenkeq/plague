package com.gover.plague.order.service;

import com.gover.plague.common.ResultVO;
import com.gover.plague.order.req.OrderPlaceReq;
import com.gover.plague.order.resp.OrderPlaceResp;

public interface OrderPlaceService {

    /**
     * 根据订单ID查找订单
     * @param req
     * @return
     */
    ResultVO<OrderPlaceResp> placeOrder(OrderPlaceReq req);
}
