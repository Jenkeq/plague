package com.gover.plague.controller;

import com.gover.plague.warehouse.resp.WareCheckResp;
import com.gover.plague.warehouse.req.WareCheckReq;
import com.gover.plague.warehouse.service.WareCheckService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public WareCheckResp check(@RequestBody WareCheckReq req){
        return warehouseCheckService.queryStock(req);
    }

}
