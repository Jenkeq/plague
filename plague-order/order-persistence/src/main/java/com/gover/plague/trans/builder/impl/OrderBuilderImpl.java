package com.gover.plague.trans.builder.impl;

import com.gover.plague.entity.Order;
import com.gover.plague.trans.builder.OrderBuilder;
import com.gover.plague.trans.dto.OrderDO;
import com.gover.plague.value.GoodsId;
import com.gover.plague.value.OrderId;
import org.springframework.stereotype.Service;

@Service
public class OrderBuilderImpl implements OrderBuilder {

    @Override
    public Order toOrder(OrderDO orderDO) {
        Order order = new Order();
        order.setId(new OrderId(orderDO.getId()));
        order.setGoodsId(new GoodsId(orderDO.getGoodsId()));
        return order;
    }
}
