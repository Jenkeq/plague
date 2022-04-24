package com.gover.plague.order.resp;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class OrderPlaceResp implements Serializable {
    int goodsId;
}
