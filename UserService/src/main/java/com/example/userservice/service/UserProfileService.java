package com.example.userservice.service;

import com.example.userservice.dto.UserProfileRequest;
import com.example.userservice.model.UserProfile;

import java.util.List;

public interface UserProfileService {
    UserProfile createUserProfile(UserProfileRequest userProfileRequest);
    UserProfile getUserProfile(Long id);
    List<UserProfile> getAllUserProfiles();
    UserProfile updateUserProfile(Long id, UserProfileRequest userProfileRequest);
    void deleteUserProfile (Long id);
}
