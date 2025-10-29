package com.example.userservice.mapper;

import com.example.userservice.dto.UserAvatarResponse;
import com.example.userservice.model.UserAvatar;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserAvatarMapper {


    @Mapping(target = "userProfileId", source = "userProfile.id")
    UserAvatarResponse toResponse (UserAvatar userAvatar);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(@MappingTarget UserAvatar existing, UserAvatar userAvatar);
}
