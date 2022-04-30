package com.gover.plague.log.service.impl;

import com.gover.plague.common.ResultVO;
import com.gover.plague.log.req.ApiAccessLogs;
import com.gover.plague.log.service.LogSendService;
import com.gover.plague.mq.service.LogSendMqService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Service
@Component
public class LogSendServiceImpl implements LogSendService {

    @Reference
    private LogSendMqService logSendMqService;

    @Override
    public ResultVO sendApiAccessLog(ApiAccessLogs req) {
        // 这里可以对日志做一些手脚
        return logSendMqService.sendApiAccessLog(req);
    }
}
