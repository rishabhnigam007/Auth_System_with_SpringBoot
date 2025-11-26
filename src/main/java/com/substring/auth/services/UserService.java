package com.substring.auth.services;

import com.substring.auth.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserById(String userId);

    UserDto getUserByEmail(String email);

    UserDto updateUser(String userId, UserDto userDto);

    void deleteUser(String userId);

    Iterable<UserDto> getAllUsers();
}
