package com.example.userservice.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String password;
}
