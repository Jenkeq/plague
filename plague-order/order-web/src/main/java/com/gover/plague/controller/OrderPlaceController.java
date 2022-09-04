package com.gover.plague.controller;

import com.gover.plague.common.ResultVO;
import com.gover.plague.order.req.OrderPlaceReq;
import com.gover.plague.order.resp.OrderPlaceResp;
import com.gover.plague.order.service.OrderPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderPlaceController {

    @Autowired
    private OrderPlaceService orderPlaceService;

    @PostMapping("/r/r1")
    public ResultVO<String> findOrder2(){
        return ResultVO.success("ok");
    }

    @PostMapping("/v1/place")
    public ResultVO<OrderPlaceResp> findOrder(@RequestBody OrderPlaceReq req){
        return orderPlaceService.placeOrder(req);
    }
}
