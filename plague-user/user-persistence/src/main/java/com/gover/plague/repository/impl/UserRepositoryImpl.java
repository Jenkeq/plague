package com.gover.plague.repository.impl;

import com.gover.plague.entity.User;
import com.gover.plague.mapper.UserMapper;
import com.gover.plague.repository.UserRepository;
import com.gover.plague.trans.builder.UserBuilder;
import com.gover.plague.trans.dto.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public UserBuilder userBuilder;

    @Override
    public User getUserByName(String name) {
        UserDO userDO = userMapper.getUserByName(name);
        return userBuilder.toUser(userDO);
    }
}
