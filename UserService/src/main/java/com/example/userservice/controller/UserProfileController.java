package com.example.userservice.controller;

import com.example.userservice.dto.UserProfileRequest;
import com.example.userservice.dto.UserProfileResponse;
import com.example.userservice.mapper.UserProfileMapper;
import com.example.userservice.model.UserProfile;
import com.example.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserProfileMapper userProfileMapper;

    @PostMapping
    public ResponseEntity<UserProfileResponse> createUserProfile(@RequestBody UserProfileRequest request) {
        UserProfile created = userProfileService.createUserProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long id) {
        UserProfile userProfile = userProfileService.getUserProfile(id);
        return ResponseEntity.ok(userProfileMapper.toResponse(userProfile));
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> getAllUserProfiles() {
        return ResponseEntity.ok(userProfileService.getAllUserProfiles()
                .stream()
                .map(userProfileMapper::toResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponse> updateUserProfile(@PathVariable Long id,
                                                                 @RequestBody UserProfileRequest request) {
        UserProfile updated = userProfileService.updateUserProfile(id, request);
        return ResponseEntity.ok(userProfileMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();
    }
}
