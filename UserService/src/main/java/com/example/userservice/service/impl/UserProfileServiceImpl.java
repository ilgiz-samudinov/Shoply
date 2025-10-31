package com.example.userservice.service.impl;

import com.example.userservice.dto.UserProfileRequest;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.exception.ResourceAlreadyExistsException;
import com.example.userservice.mapper.UserProfileMapper;
import com.example.userservice.model.User;
import com.example.userservice.model.UserProfile;
import com.example.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserService userService;

    @Transactional
    public UserProfile createUserProfile(UserProfileRequest userProfileRequest) {
        User user = userService.getUserById(userProfileRequest.getUserId());

        if (userProfileRepository.existsByUser_Id(user.getId())) {
            throw new ResourceAlreadyExistsException("User profile already exists for user id " + user.getId());
        }

        UserProfile userProfile = userProfileMapper.toEntity(userProfileRequest);
        userProfile.setUser(user);

        return userProfileRepository.save(userProfile);
    }



    @Transactional(readOnly = true)
    public UserProfile getUserProfile(Long id){
        return userProfileRepository.findById(id).orElseThrow(()-> new NotFoundException("User profile not found"));
    }


    @Transactional(readOnly = true)
    public List<UserProfile> getAllUserProfiles(){
        return userProfileRepository.findAll();
    }


    @Transactional
    public UserProfile updateUserProfile(Long id, UserProfileRequest userProfileRequest){
        UserProfile existing = getUserProfile(id);
        UserProfile userProfile = userProfileMapper.toEntity(userProfileRequest);
        userProfileMapper.merge(existing, userProfile);
        return userProfileRepository.save(existing);
    }



    @Transactional
    public void deleteUserProfile (Long id){
        if(!userProfileRepository.existsById(id)){
            throw new NotFoundException("User profile not found");
        }
        userProfileRepository.deleteById(id);
    }

}
