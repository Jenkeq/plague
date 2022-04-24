package com.gover.plague;

import com.gover.plague.req.OrderPlaceReq;
import com.gover.plague.resp.OrderPlaceResp;
import org.springframework.http.ResponseEntity;

public interface OrderPlaceService {

    /**
     * 根据订单ID查找订单
     * @param req
     * @return
     */
    ResponseEntity<OrderPlaceResp> findOrder(OrderPlaceReq req);
}
