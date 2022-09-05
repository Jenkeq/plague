package com.gover.plague.user.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResRoleResp implements Serializable {
    String roleName;
    String url;
}
