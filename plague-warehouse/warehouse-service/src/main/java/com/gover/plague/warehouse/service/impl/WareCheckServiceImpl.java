package com.gover.plague.warehouse.service.impl;

import com.gover.plague.entity.WareHouse;
import com.gover.plague.order.req.OrderPlaceReq;
import com.gover.plague.order.service.OrderPlaceService;
import com.gover.plague.repository.WareCheckRepository;
import com.gover.plague.value.GoodsId;
import com.gover.plague.warehouse.req.WareCheckReq;
import com.gover.plague.warehouse.resp.WareCheckResp;
import com.gover.plague.warehouse.service.WareCheckService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class WareCheckServiceImpl implements WareCheckService {

    @Autowired
    private WareCheckRepository wareCheckRepository;

//    @Reference(check = false)
//    private OrderPlaceService orderPlaceService;

    // 事件发布器

    @Override
    public WareCheckResp queryStock(WareCheckReq req) {
        System.out.println("进入warehouse模块的的WareCheckServiceImpl.queryStock");
        WareHouse wareHouse = wareCheckRepository.queryStock(new GoodsId(req.getGoodsId()));

        // 返回
        return new WareCheckResp();
    }

}
