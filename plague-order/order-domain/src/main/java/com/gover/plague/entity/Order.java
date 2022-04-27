package com.gover.plague.entity;

import com.gover.plague.value.OrderItem;
import lombok.Data;

@Data
public class Order {

    int id;

    OrderItem orItem;
}
