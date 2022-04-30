package com.gover.plague.controller;

import com.gover.plague.common.ResultVO;
import com.gover.plague.log.service.LogSendService;
import com.gover.plague.mq.req.OrderLogReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log")
public class LogSendController {

    @Autowired
    private LogSendService logSendService;

    @RequestMapping("/order/place")
    public ResultVO orderSendLog(@RequestBody OrderLogReq req){
        return logSendService.sendOrderLog(req);
    }
}
