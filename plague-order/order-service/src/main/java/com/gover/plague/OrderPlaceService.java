package com.gover.plague;

import com.gover.plague.entity.Order;
import com.gover.plague.req.OrderPlaceReq;
import com.gover.plague.value.OrderId;
import com.gover.plague.vo.OrderPlaceVO;
import org.springframework.http.ResponseEntity;

public interface OrderPlaceService {

    /**
     * 根据订单ID查找订单
     * @param req
     * @return
     */
    ResponseEntity<OrderPlaceVO> findOrder(OrderPlaceReq req);
}
