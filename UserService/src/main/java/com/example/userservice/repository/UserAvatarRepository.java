package com.example.userservice.repository;

import com.example.userservice.model.UserAvatar;
import com.example.userservice.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatar, Long> {
    UserAvatar findByUserProfile(UserProfile userProfile);
//    UserAvatar findByUserProfileId(Long id);
}
