package com.gover.plague.trans.dto;

import lombok.Data;

@Data
public class UserDO {
    String id;
    String userName;
    String password;
    String roleName;
    String status;
}
