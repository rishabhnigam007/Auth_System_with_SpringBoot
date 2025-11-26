package com.substring.auth.services.impl;

import com.substring.auth.dto.UserDto;
import com.substring.auth.entities.Provider;
import com.substring.auth.entities.User;
import com.substring.auth.exceptions.ResourceNotFoundException;
import com.substring.auth.helpers.UserHelper;
import com.substring.auth.repositories.UserRepository;
import com.substring.auth.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {

        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setProvider(userDto.getProvider() != null ? userDto.getProvider() : Provider.LOCAL);
        // role assign here to new user for authentication and authorization
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(UserHelper.parseUUID(userId)).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<User> findByEmail = Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email)));
        return modelMapper.map(findByEmail, UserDto.class);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        UUID uId = UserHelper.parseUUID(userId);
        User existingUser = userRepository
                .findById(uId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        //we are not going to change email id for this project.
        if (userDto.getName() != null) existingUser.setName(userDto.getName());
        if (userDto.getImage() != null) existingUser.setImage(userDto.getImage());
        if (userDto.getProvider() != null) existingUser.setProvider(userDto.getProvider());
        //TODO: change password updation logic...
        if (userDto.getPassword() != null) existingUser.setPassword(userDto.getPassword());
//        existingUser.setEnable(userDto.isEnable());
        existingUser.setUpdatedAt(Instant.now());
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserDto.class);

    }

    @Override
    public void deleteUser(String userId) {
        UUID uuid = UserHelper.parseUUID(userId);
        User user = userRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.deleteById(uuid);
    }

    @Override
    @Transactional
    public Iterable<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
