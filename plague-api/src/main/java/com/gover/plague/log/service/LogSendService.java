package com.gover.plague.log.service;

import com.gover.plague.common.ResultVO;
import com.gover.plague.log.req.ApiAccessLogs;

public interface LogSendService {

    ResultVO sendApiAccessLog(ApiAccessLogs req);
}
