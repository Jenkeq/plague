package com.gover.plague.mq.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderLogReq implements Serializable {
    String orderId;
    String goodsId;
    String goodsName;
}
