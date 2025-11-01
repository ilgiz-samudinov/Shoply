package com.example.userservice.controller;

import com.example.userservice.dto.UserAvatarDownload;
import com.example.userservice.dto.UserAvatarResponse;
import com.example.userservice.mapper.UserAvatarMapper;
import com.example.userservice.model.UserAvatar;
import com.example.userservice.service.UserAvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-avatars")
public class UserAvatarController {

    private final UserAvatarService userAvatarService;
    private final UserAvatarMapper userAvatarMapper;


    @PostMapping("/{userProfileId}")
    public ResponseEntity<UserAvatarResponse> uploadAvatar(@PathVariable Long userProfileId,
                                                           @RequestParam("file") MultipartFile file) {
        UserAvatar userAvatar = userAvatarService.uploadUserAvatar(userProfileId, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(userAvatarMapper.toResponse(userAvatar));
    }



    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponseBody> downloadAvatar(@PathVariable Long id) {
        UserAvatarDownload userAvatarDownload = userAvatarService.downloadUserAvatar(id);

        StreamingResponseBody stream = outputStream -> {
            try (InputStream inputStream = userAvatarDownload.getInputStream()) {
                inputStream.transferTo(outputStream);
            }
        };

        return ResponseEntity.ok()
                .contentType(userAvatarDownload.getMediaType())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + userAvatarDownload.getUrl() + "\"")
                .body(stream);

    }
}

