package com.gover.plague.trans.builder;

import com.gover.plague.entity.ResRole;
import com.gover.plague.entity.User;
import com.gover.plague.trans.dto.ResRoleDO;
import com.gover.plague.trans.dto.UserDO;
import com.gover.plague.user.resp.ResRoleResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBuilder {

    public User toUser(UserDO userDO){
        User user = new User();
        BeanUtils.copyProperties(userDO, user);
        return user;
    }

    public User toUser(List<UserDO> userDOs){
        User user = new User();
        if(userDOs == null || userDOs.size() == 0){
            return user;
        }
        List<String> roles = new ArrayList<>();
        for (UserDO userDO : userDOs) {
            roles.add(userDO.getRoleName());
        }
        BeanUtils.copyProperties(userDOs.get(0), user);
        user.setRoles(roles);
        return user;
    }

    public ResRole toResRole(ResRoleDO resRoleDO) {
        ResRole resRole = new ResRole();
        BeanUtils.copyProperties(resRoleDO, resRole);
        return resRole;
    }
}
