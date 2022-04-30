package com.gover.plague.mq.service;

import com.gover.plague.common.ResultVO;
import com.gover.plague.mq.req.OrderLogReq;

public interface LogSendMqService {
    ResultVO sendOrderLog(OrderLogReq req);
}
