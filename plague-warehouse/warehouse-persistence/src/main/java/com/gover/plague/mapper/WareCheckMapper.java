package com.gover.plague.mapper;

import com.gover.plague.trans.dto.WareHouseDO;
import com.gover.plague.value.GoodsId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WareCheckMapper {

    /**
     * 检查库存
     * @param goodsId
     * @return
     */
    WareHouseDO queryStock(@Param("req") GoodsId goodsId);
}
