package com.gover.plague.repository;

import com.gover.plague.entity.Order;

public interface OrderPlaceRepository {

    Order findOrder(int orderId);
}
