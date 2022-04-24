package com.gover.plague.order.service;

import com.gover.plague.order.req.OrderPlaceReq;
import com.gover.plague.order.resp.OrderPlaceResp;
import org.springframework.http.ResponseEntity;

public interface OrderPlaceService {

    /**
     * 根据订单ID查找订单
     * @param req
     * @return
     */
    ResponseEntity<OrderPlaceResp> findOrder(OrderPlaceReq req);
}
