package com.gover.plague.trans.builder;

import com.gover.plague.entity.User;
import com.gover.plague.trans.dto.UserDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

    public User toUser(UserDO userDO){
        User user = new User();
        BeanUtils.copyProperties(userDO, user);
        return user;
    }
}
