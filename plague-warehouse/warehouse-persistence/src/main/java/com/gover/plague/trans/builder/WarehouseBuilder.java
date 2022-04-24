package com.gover.plague.trans.builder;

import com.gover.plague.entity.WareHouse;
import com.gover.plague.trans.dto.WareHouseDO;

public interface WarehouseBuilder {

    WareHouse toWareHouse(WareHouseDO wareHouseDO);
}
