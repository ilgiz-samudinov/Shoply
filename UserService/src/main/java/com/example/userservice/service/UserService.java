package com.example.userservice.service;

import com.example.userservice.dto.UserRequest;
import com.example.userservice.model.User;

import java.util.List;

public interface UserService {
    User createUser(UserRequest userRequest);
    List<User> getAllUsers();
    User updateUser(Long id, UserRequest userRequest);
    User getUserById(Long id);
    void deleteUser(Long id);
}
