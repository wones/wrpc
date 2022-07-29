package com.wones.service.impl;

import com.wones.common.User;
import com.wones.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id) {
        User user = new User(id,"wones","123");
        return user;
    }
}
