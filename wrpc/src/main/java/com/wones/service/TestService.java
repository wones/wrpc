package com.wones.service;

import com.wones.service.impl.UserServiceImpl;

public class TestService {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        System.out.println(userService.getUserById(1));
    }
}
