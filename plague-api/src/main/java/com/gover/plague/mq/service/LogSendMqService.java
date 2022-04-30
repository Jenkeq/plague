package com.gover.plague.mq.service;

import com.gover.plague.common.ResultVO;
import com.gover.plague.log.req.ApiAccessLogs;

public interface LogSendMqService {

    /**
     * API访问日志发送服务
     * @param req
     * @return
     */
    ResultVO sendApiAccessLog(ApiAccessLogs req);
}
