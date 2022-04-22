package com.gover.plague.controller;

import com.gover.plague.OrderPlaceService;
import com.gover.plague.entity.Order;
import com.gover.plague.req.OrderPlaceReq;
import com.gover.plague.value.OrderId;
import com.gover.plague.vo.OrderPlaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order/place/")
public class OrderPlaceController {

    @Autowired
    private OrderPlaceService orderPlaceService;

    @PostMapping("find")
    public OrderPlaceVO findOrder(@RequestBody OrderPlaceReq req){
        return orderPlaceService.findOrder(req);
    }
}
