package com.gover.plague.order.service.impl;

import com.gover.plague.common.ResultVO;
import com.gover.plague.entity.Order;
import com.gover.plague.log.annotation.ApiAccessLog;
import com.gover.plague.order.req.OrderPlaceReq;
import com.gover.plague.order.resp.OrderPlaceResp;
import com.gover.plague.order.service.OrderPlaceService;
import com.gover.plague.repository.OrderPlaceRepository;
import com.gover.plague.warehouse.req.WareCheckReq;
import com.gover.plague.warehouse.resp.WareCheckResp;
import com.gover.plague.warehouse.service.WareCheckService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Service
@Component
public class OrderPlaceServiceImpl implements OrderPlaceService {

    @Autowired
    private OrderPlaceRepository orderPlaceRepository;

    @Reference(check = false)
    private WareCheckService wareCheckService;

    // 事件发布器
    // 领域服务
    // 其他模块服务

    /**
     * 根据订单ID查找订单
     * @param req
     * @return
     */
    @Override
    // @ApiAccessLog(desc = "根据订单ID查找订单")
    public ResultVO<OrderPlaceResp> placeOrder(OrderPlaceReq req) {
        // 查询库存
        WareCheckReq wareCheckReq = new WareCheckReq();
        wareCheckReq.setGoodsId(1);
        WareCheckResp wareCheckResp = wareCheckService.queryStock(wareCheckReq);
        System.out.println(wareCheckResp.getStockLeft());

        Order order = orderPlaceRepository.placeOrder(req.getOrderId());

        // 返回
        return ResultVO.success(OrderPlaceResp.builder()
                .stock(wareCheckResp.getStockLeft())
                .build());
    }
}
