package com.gover.plague.controller;

import com.gover.plague.order.req.OrderPlaceReq;
import com.gover.plague.order.resp.OrderPlaceResp;
import com.gover.plague.order.service.OrderPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order/place")
public class OrderPlaceController {

    @Autowired
    private OrderPlaceService orderPlaceService;

    @PostMapping("/v1/find")
    public ResponseEntity<OrderPlaceResp> findOrder(@RequestBody OrderPlaceReq req){
        return orderPlaceService.findOrder(req);
    }
}
