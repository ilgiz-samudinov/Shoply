package com.example.userservice.service;

import com.example.userservice.dto.UserAvatarDownload;
import com.example.userservice.model.UserAvatar;
import org.springframework.web.multipart.MultipartFile;


public interface UserAvatarService {
    UserAvatar uploadUserAvatar(Long userProfileId, MultipartFile file);

    UserAvatarDownload downloadUserAvatar(Long id);

    UserAvatar getUserAvatarById(Long id);
}
