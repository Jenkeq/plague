package com.gover.plague.warehouse.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.gover.plague.entity.WareHouse;
import com.gover.plague.repository.WareCheckRepository;
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

    // 领域事件发布器

    /**
     * 查询库存
     * @param req
     * @return
     */
    @Override
    @SentinelResource(value="WareCheckService.queryStock",
            blockHandler = "queryStockBlock", blockHandlerClass = WarehouseBlockHandler.class,
            fallback = "queryStockFallback", fallbackClass = WarehouseFallback.class)
    public WareCheckResp queryStock(WareCheckReq req) {
        System.out.println("进入warehouse模块的的WareCheckServiceImpl.queryStock方法");
        WareHouse wareHouse = wareCheckRepository.queryStock(req.getGoodsId());
        return new WareCheckResp(wareHouse.getStock());
    }

}
