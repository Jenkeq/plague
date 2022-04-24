package com.gover.plague.impl;

import com.gover.plague.WareCheckService;
import com.gover.plague.entity.WareHouse;
import com.gover.plague.repository.WareCheckRepository;
import com.gover.plague.req.WareCheckReq;
import com.gover.plague.resp.WareCheckResp;
import com.gover.plague.value.GoodsId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WareCheckServiceImpl implements WareCheckService {

    @Autowired
    private WareCheckRepository wareCheckRepository;

    // 事件发布器

    @Override
    public ResponseEntity<WareCheckResp> queryStock(WareCheckReq req) {
        WareHouse wareHouse = wareCheckRepository.queryStock(new GoodsId(req.getGoodsId()));

        // 返回
        return ResponseEntity.ok(WareCheckResp.builder().stockLeft(wareHouse.getStock().getStock()).build());
    }
}
