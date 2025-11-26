package com.substring.auth.controllers;

import com.substring.auth.dto.UserDto;
import com.substring.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getAllUsers() {
        // Implementation for getting all users would go here
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
