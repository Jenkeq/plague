package com.gover.plague.repository.impl;

import com.gover.plague.entity.Order;
import com.gover.plague.repository.OrderPlaceRepository;
import com.gover.plague.mapper.OrderPlaceMapper;
import com.gover.plague.trans.builder.OrderBuilder;
import com.gover.plague.trans.dto.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderPlaceRepositoryImpl implements OrderPlaceRepository {

    @Autowired
    private OrderPlaceMapper orderPlaceMapper;

    @Autowired
    private OrderBuilder orderBuilder;

    @Override
    public Order findOrder(int orderId) {
        OrderDO orderDO = orderPlaceMapper.findOrder(orderId);
        return orderBuilder.toOrder(orderDO);
    }
}
