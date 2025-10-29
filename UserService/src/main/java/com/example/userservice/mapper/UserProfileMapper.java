package com.example.userservice.mapper;

import com.example.userservice.dto.UserProfileRequest;
import com.example.userservice.dto.UserProfileResponse;
import com.example.userservice.model.UserProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toEntity (UserProfileRequest userProfileRequest);

    @Mapping(target = "userId", source = "user.id")
    UserProfileResponse toResponse (UserProfile userProfile);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(@MappingTarget UserProfile existing, UserProfile userProfile);
}
