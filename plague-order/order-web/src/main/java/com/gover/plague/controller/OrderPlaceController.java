package com.gover.plague.controller;

import com.gover.plague.common.ResultVO;
import com.gover.plague.order.req.OrderPlaceReq;
import com.gover.plague.order.resp.OrderPlaceResp;
import com.gover.plague.order.service.OrderPlaceService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

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
    public ResultVO<OrderPlaceResp> findOrder(@RequestBody OrderPlaceReq req,@RequestHeader HttpHeaders headers){
        String s = new String(Base64.decode(headers.getFirst("token-info")));
        System.out.println(s);
        return orderPlaceService.placeOrder(req);
    }
}
