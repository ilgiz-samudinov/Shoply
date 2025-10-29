package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class UserAvatarResponse {
    private Long id;

    private String avatarUrl;

    private Long userProfileId;

    private Instant uploadedAt;
}
