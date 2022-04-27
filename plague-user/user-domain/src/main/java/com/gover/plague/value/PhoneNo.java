package com.gover.plague.value;

import lombok.Data;

@Data
public class PhoneNo {
    String phoneNo;
    // 运营商
    String operator;
    // 归属地（0大陆，1台湾，2香港，3澳门，4国外）
    String attribution;
}
