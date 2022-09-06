package com.gover.plague.controller;

import com.gover.plague.auth.annotation.AccessVerify;
import com.gover.plague.common.ResultVO;
import com.gover.plague.log.annotation.ApiAccessLog;
import com.gover.plague.order.req.OrderPlaceReq;
import com.gover.plague.order.resp.OrderPlaceResp;
import com.gover.plague.order.service.OrderPlaceService;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

import static com.gover.plague.constant.AccessType.GATEWAY;

@RestController
@RequestMapping("/api/order")
public class OrderPlaceController {

    @Autowired
    private OrderPlaceService orderPlaceService;

    @PostMapping("/v1/place")
    @AccessVerify
    // @ApiAccessLog(desc = "根据订单ID查找订单")
    public ResultVO<OrderPlaceResp> findOrder(@RequestBody OrderPlaceReq req,@RequestHeader HttpHeaders headers){
        String s = new String(Base64.decode(headers.getFirst("token-info")));
        System.out.println(s);
        return orderPlaceService.placeOrder(req);
    }
}
