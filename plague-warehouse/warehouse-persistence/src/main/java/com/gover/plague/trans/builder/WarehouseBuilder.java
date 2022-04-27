package com.gover.plague.trans.builder;

import com.gover.plague.entity.WareHouse;
import com.gover.plague.trans.dto.WareHouseDO;
import org.springframework.stereotype.Component;

@Component
public class WarehouseBuilder {

    public WareHouse toWareHouse(WareHouseDO wareHouseDO) {
        WareHouse wareHouse = new WareHouse();
        wareHouse.setId(wareHouseDO.getId());
        wareHouse.setGoodsId(wareHouseDO.getGoodsId());
        wareHouse.setStock(wareHouseDO.getStock());
        return wareHouse;
    }
}
