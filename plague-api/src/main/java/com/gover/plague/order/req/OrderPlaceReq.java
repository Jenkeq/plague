package com.gover.plague.order.req;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class OrderPlaceReq implements Serializable {

    int orderId;
}
