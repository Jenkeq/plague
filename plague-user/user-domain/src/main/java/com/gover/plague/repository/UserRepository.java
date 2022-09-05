package com.gover.plague.repository;

import com.gover.plague.entity.ResRole;
import com.gover.plague.entity.User;
import com.gover.plague.user.resp.ResRoleResp;

import java.util.List;

public interface UserRepository {

    User getUserByName(String name);

    List<ResRole> getAllResRole();
}
