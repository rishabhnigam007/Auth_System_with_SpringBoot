package com.substring.auth.services.impl;

import com.substring.auth.dto.UserDto;
import com.substring.auth.services.AuthService;
import com.substring.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Override
    public UserDto registerUser(UserDto userDto) {
        //logic
        //verify email
        //verify password
        //verify role
        UserDto userDto1 = userService.createUser(userDto);
        return userDto1;
    }
}
