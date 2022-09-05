package com.gover.plague.repository.impl;

import com.gover.plague.entity.ResRole;
import com.gover.plague.entity.User;
import com.gover.plague.mapper.UserMapper;
import com.gover.plague.repository.UserRepository;
import com.gover.plague.trans.builder.UserBuilder;
import com.gover.plague.trans.dto.ResRoleDO;
import com.gover.plague.trans.dto.UserDO;
import com.gover.plague.user.resp.ResRoleResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public UserBuilder userBuilder;

    @Override
    public User getUserByName(String name) {
        List<UserDO> userDOs = userMapper.getUserByName(name);
        return userBuilder.toUser(userDOs);
    }

    @Override
    public List<ResRole> getAllResRole() {
        List<ResRoleDO> allResRole = userMapper.getAllResRole();
        List<ResRole> rel = new ArrayList<>();
        for (ResRoleDO resRoleDO : allResRole) {
            ResRole resRole = userBuilder.toResRole(resRoleDO);
            rel.add(resRole);
        }
        return rel;
    }
}
