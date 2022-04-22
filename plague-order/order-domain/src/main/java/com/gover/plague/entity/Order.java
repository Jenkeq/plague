package com.gover.plague.entity;

import com.gover.plague.value.GoodsId;
import com.gover.plague.value.OrderId;
import lombok.Data;

@Data
public class Order {
    OrderId id;
    GoodsId goodsId;
}
