package com.gover.plague.repository;

import com.gover.plague.entity.Order;

public interface OrderPlaceRepository {

    Order placeOrder(int orderId);
}
