package com.gover.plague.trans.builder.impl;

import com.gover.plague.entity.WareHouse;
import com.gover.plague.trans.builder.WarehouseBuilder;
import com.gover.plague.trans.dto.WareHouseDO;
import com.gover.plague.value.GoodsId;
import com.gover.plague.value.Stock;
import com.gover.plague.value.WareHouseId;
import org.springframework.stereotype.Service;

@Service
public class WarehouseBuilderImpl implements WarehouseBuilder {

    @Override
    public WareHouse toWareHouse(WareHouseDO wareHouseDO) {
        WareHouse wareHouse = new WareHouse();
        wareHouse.setId(new WareHouseId(wareHouseDO.getId()));
        wareHouse.setGoodsId(new GoodsId(wareHouseDO.getGoodsId()));
        wareHouse.setStock(new Stock(wareHouseDO.getStock()));
        return wareHouse;
    }
}
