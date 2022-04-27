package com.gover.plague.trans.builder;

import com.gover.plague.entity.Order;
import com.gover.plague.trans.dto.OrderDO;
import org.springframework.stereotype.Component;

@Component
public class OrderBuilder {

    public Order toOrder(OrderDO orderDO) {
        Order order = new Order();
        order.setId(orderDO.getOrderId());
        return order;
    }
}
