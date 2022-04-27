package com.gover.plague.repository;

import com.gover.plague.entity.WareHouse;

public interface WareCheckRepository {

    WareHouse queryStock(int goodsId);
}
