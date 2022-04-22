package com.gover.plague.impl;

import com.gover.plague.entity.Order;
import com.gover.plague.repository.OrderPlaceRepository;
import com.gover.plague.OrderPlaceService;
import com.gover.plague.req.OrderPlaceReq;
import com.gover.plague.value.OrderId;
import com.gover.plague.vo.OrderPlaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderPlaceServiceImpl implements OrderPlaceService {

    @Autowired
    private OrderPlaceRepository orderPlaceRepository;

    // 事件发布器
    // 领域服务
    // 其他模块服务

    /**
     * 根据订单ID查找订单
     * @param req
     * @return
     */
    @Override
    public ResponseEntity<OrderPlaceVO> findOrder(OrderPlaceReq req) {
        Order order = orderPlaceRepository.findOrder(new OrderId(req.getOrderId()));

        // 返回
        return ResponseEntity.ok(OrderPlaceVO.builder().goodsId(order.getGoodsId().getId()).build());
    }
}
