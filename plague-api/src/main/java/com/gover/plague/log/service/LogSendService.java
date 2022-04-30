package com.gover.plague.log.service;

import com.gover.plague.common.ResultVO;
import com.gover.plague.mq.req.OrderLogReq;

public interface LogSendService {

    ResultVO sendOrderLog(OrderLogReq req);
}
