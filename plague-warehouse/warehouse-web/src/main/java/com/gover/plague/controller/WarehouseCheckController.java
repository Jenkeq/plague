package com.gover.plague.controller;

import com.gover.plague.WareCheckService;
import com.gover.plague.req.WareCheckReq;
import com.gover.plague.resp.WareCheckResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse/check")
public class WarehouseCheckController {

    @Autowired
    private WareCheckService warehouseCheckService;

    @PostMapping(value = "/v1/queryStock")
    public ResponseEntity<WareCheckResp> check(@RequestBody WareCheckReq req){
        return warehouseCheckService.queryStock(req);
    }
}
