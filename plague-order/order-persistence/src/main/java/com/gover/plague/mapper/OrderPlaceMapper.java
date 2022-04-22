package com.gover.plague.mapper;

import com.gover.plague.trans.dto.OrderDO;
import com.gover.plague.value.OrderId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface OrderPlaceMapper {

    OrderDO findOrder(@Param("orderId") OrderId id);
}
