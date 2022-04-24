package com.gover.plague.entity;

import com.gover.plague.value.GoodsId;
import com.gover.plague.value.Stock;
import com.gover.plague.value.WareHouseId;
import lombok.Data;

@Data
public class WareHouse {
    WareHouseId id;
    GoodsId goodsId;
    Stock stock;
}
