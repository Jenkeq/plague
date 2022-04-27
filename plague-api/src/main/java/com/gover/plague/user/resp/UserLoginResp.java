package com.gover.plague.user.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResp {

    int id;
    String userName;
    String password;
    String token;
}
