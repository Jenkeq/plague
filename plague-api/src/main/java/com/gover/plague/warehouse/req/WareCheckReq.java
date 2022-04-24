package com.gover.plague.warehouse.req;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WareCheckReq implements Serializable {

    int goodsId;
}
