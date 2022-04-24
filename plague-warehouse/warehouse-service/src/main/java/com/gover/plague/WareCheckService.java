package com.gover.plague;

import com.gover.plague.req.WareCheckReq;
import com.gover.plague.resp.WareCheckResp;
import org.springframework.http.ResponseEntity;

public interface WareCheckService {

    ResponseEntity<WareCheckResp> queryStock(WareCheckReq req);
}
