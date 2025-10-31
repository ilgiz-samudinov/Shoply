package com.example.userservice.service.impl;


import com.example.userservice.dto.UserRequest;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Transactional
    public User createUser(UserRequest userRequest) {
        User user = userMapper.toEntity(userRequest);
        return userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public User updateUser(Long id, UserRequest userRequest) {
        User updated = userMapper.toEntity(userRequest);
        User existing = getUserById(id);
        userMapper.merge(existing, updated);
        return userRepository.save(existing);
    }


    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User with id " + id + " not found"));
    }


    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

}
