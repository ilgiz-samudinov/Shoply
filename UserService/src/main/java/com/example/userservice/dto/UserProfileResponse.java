package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private Long userId;
}
