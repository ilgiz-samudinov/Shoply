package com.example.userservice.controller;


import com.example.userservice.dto.UserRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest){
        User createdUser = userService.createUser(userRequest);
        return userMapper.toResponse(createdUser);
    }


    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers().stream().map(userMapper::toResponse).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest){
        User updatedUser  = userService.updateUser(id, userRequest);
        return userMapper.toResponse(updatedUser);
    }


    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return userMapper.toResponse(userService.getUserById(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
