package com.gover.plague.trans.builder;

import com.gover.plague.entity.Order;
import com.gover.plague.trans.dto.OrderDO;
import net.sf.jsqlparser.expression.OrderByClause;

public interface OrderBuilder {

    Order toOrder(OrderDO orderDO);
}
