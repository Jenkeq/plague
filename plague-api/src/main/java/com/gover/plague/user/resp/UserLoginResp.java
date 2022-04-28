package com.gover.plague.user.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResp implements Serializable {

    String id;
    String userName;
    String password;
    String status;
    String token;
    List<String> roles;
}
