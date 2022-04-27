package com.gover.plague.entity;

import com.gover.plague.value.CurAddress;
import com.gover.plague.value.Email;
import com.gover.plague.value.PhoneNo;
import com.gover.plague.value.ShipAdress;
import lombok.Data;

import java.util.List;

@Data
public class User {

    int id;
    String userName;
    String nickName;
    String password;
    PhoneNo phoneNo;
    Email email;
    CurAddress curAddress;
    List<ShipAdress> shipAdds;
    String status;

    /**
     * 登录
     */
    public void login(){

    }
}
