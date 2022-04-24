package com.gover.plague.repository;

import com.gover.plague.entity.WareHouse;
import com.gover.plague.value.GoodsId;

public interface WareCheckRepository {

    WareHouse queryStock(GoodsId goodsId);
}
