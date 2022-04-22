package com.gover.plague.repository;

import com.gover.plague.entity.Order;
import com.gover.plague.value.OrderId;

public interface OrderPlaceRepository {

    Order findOrder(OrderId orderId);
}
