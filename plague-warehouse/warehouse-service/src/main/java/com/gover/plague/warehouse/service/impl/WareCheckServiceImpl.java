package com.gover.plague.warehouse.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.gover.plague.entity.WareHouse;
import com.gover.plague.repository.WareCheckRepository;
import com.gover.plague.value.GoodsId;
import com.gover.plague.warehouse.req.WareCheckReq;
import com.gover.plague.warehouse.resp.WareCheckResp;
import com.gover.plague.warehouse.service.WareCheckService;
import com.gover.plague.warehouse.service.sentinel.block.WarehouseBlockHandler;
import com.gover.plague.warehouse.service.sentinel.fallback.WarehouseFallback;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class WareCheckServiceImpl implements WareCheckService {

    @Autowired
    private WareCheckRepository wareCheckRepository;

    // 事件发布器

    @Override
    @SentinelResource(value="WareCheckService.queryStock",
            blockHandler = "queryStockBlock", blockHandlerClass = WarehouseBlockHandler.class,
            fallback = "queryStockFallback", fallbackClass = WarehouseFallback.class)
    public WareCheckResp queryStock(WareCheckReq req) {
        System.out.println("进入warehouse模块的的WareCheckServiceImpl.queryStock");
        WareHouse wareHouse = wareCheckRepository.queryStock(new GoodsId(req.getGoodsId()));

        // 返回
        return new WareCheckResp(wareHouse.getStock().getStock());
    }


}
