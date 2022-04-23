package com.gover.plague.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gover.plague.trans.dto.OrderDO;
import com.gover.plague.value.OrderId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface OrderPlaceMapper extends BaseMapper<OrderDO> {

    OrderDO findOrder(@Param("orderId") OrderId id);
}
