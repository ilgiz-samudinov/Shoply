package com.example.userservice.service.impl;

import com.example.userservice.dto.UserAvatarDownload;
import com.example.userservice.event.FileDeleteEvent;
import com.example.userservice.exception.NotFoundException;
import com.example.userservice.mapper.UserAvatarMapper;
import com.example.userservice.model.UserAvatar;
import com.example.userservice.model.UserProfile;
import com.example.userservice.port.FileStorage;
import com.example.userservice.repository.UserAvatarRepository;
import com.example.userservice.service.UserAvatarService;
import com.example.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Clock;
import java.time.Instant;


@Service
@RequiredArgsConstructor
public class UserAvatarServiceImpl implements UserAvatarService {

    private final FileStorage fileStorage;
    private final UserAvatarRepository userAvatarRepository;
    private final UserAvatarMapper userAvatarMapper;
    private final UserProfileService userProfileService;
    private final Clock clock;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Value("${minio.bucket.name.avatar}")
    private String bucketName;

    @Transactional
    public UserAvatar uploadUserAvatar(Long userProfileId, MultipartFile file) {
        UserProfile userProfile = userProfileService.getUserProfile(userProfileId);
        String avatarUrl = fileStorage.upload(userProfileId, file, bucketName);
        Instant now = Instant.now(clock);

        UserAvatar userAvatar = userAvatarRepository.findByUserProfile(userProfile);
        if (userAvatar == null) {
            userAvatar = UserAvatar.builder().userProfile(userProfile).avatarUrl(avatarUrl).uploadedAt(now).build();
        } else {
            String oldAvatarUrl = userAvatar.getAvatarUrl();
            userAvatar.setAvatarUrl(avatarUrl);
            userAvatar.setUploadedAt(now);
            userAvatarRepository.save(userAvatar);
            applicationEventPublisher.publishEvent(new FileDeleteEvent(oldAvatarUrl, bucketName));
        }
        return userAvatarRepository.save(userAvatar);
    }


    public UserAvatarDownload downloadUserAvatar(Long id) {
        UserAvatar userAvatar = getUserAvatarById(id);
        String urlAvatar = userAvatar.getAvatarUrl();
        InputStream inputStream = fileStorage.download(bucketName, urlAvatar);
        String contentType = fileStorage.getContentType(bucketName, urlAvatar);
        MediaType mediaType = MediaType.parseMediaType(contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);
        return UserAvatarDownload.builder().inputStream(inputStream).contentType(contentType).mediaType(mediaType).url(urlAvatar).build();
    }


    @Transactional(readOnly = true)
    public UserAvatar getUserAvatarById(Long id) {
        return userAvatarRepository.findById(id).orElseThrow(() -> new NotFoundException("User avatar not found"));
    }
}
