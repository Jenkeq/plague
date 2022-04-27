package com.gover.plague.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gover.plague.trans.dto.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderPlaceMapper extends BaseMapper<OrderDO> {

    OrderDO findOrder(@Param("req") int id);
}
