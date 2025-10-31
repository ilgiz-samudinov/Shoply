package com.example.userservice.port;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorage {
    String upload(Long userProfileId, MultipartFile file, String bucketName);
    InputStream download(String bucketName, String key);
    String getContentType(String bucketName, String key);
    void delete(String bucketName, String key);
}
