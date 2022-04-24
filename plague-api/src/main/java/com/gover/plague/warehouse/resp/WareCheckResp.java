package com.gover.plague.warehouse.resp;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class WareCheckResp implements Serializable {

    int stockLeft;
}
