package com.gover.plague.repository;

import com.gover.plague.entity.User;

public interface UserRepository {
    User getUserByName(String name);
}
