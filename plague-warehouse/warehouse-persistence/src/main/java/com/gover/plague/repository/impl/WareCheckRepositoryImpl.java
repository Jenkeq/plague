package com.gover.plague.repository.impl;

import com.gover.plague.entity.WareHouse;
import com.gover.plague.mapper.WareCheckMapper;
import com.gover.plague.repository.WareCheckRepository;
import com.gover.plague.trans.builder.WarehouseBuilder;
import com.gover.plague.trans.dto.WareHouseDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WareCheckRepositoryImpl implements WareCheckRepository {

    @Autowired
    private WareCheckMapper wareCheckMapper;

    @Autowired
    private WarehouseBuilder warehouseBuilder;

    @Override
    public WareHouse queryStock(int goodsId) {
        WareHouseDO wareHouseDO = wareCheckMapper.queryStock(goodsId);
        return warehouseBuilder.toWareHouse(wareHouseDO);
    }
}
