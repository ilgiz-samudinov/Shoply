package com.example.userservice.adapter;

import com.example.userservice.exception.FileStorageException;
import com.example.userservice.port.FileStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3FileStorage implements FileStorage {

    private final S3Client s3Client;


    public String upload(Long userProfileId, MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("file is empty");
        }

        String key = buildFileName(userProfileId, file);

        try {
            ensureBucketExists(bucketName);
            try (InputStream inputStream = file.getInputStream()) {
                s3Client.putObject(
                        PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .contentType(file.getContentType())
                                .build(),
                        RequestBody.fromInputStream(file.getInputStream(), file.getSize())
                );
            }
            return key;
        } catch (IOException | S3Exception exception) {
            throw new RuntimeException("Error uploading file: " + exception.getMessage(), exception);
        }
    }



    private void ensureBucketExists (String bucketName) {
        boolean exists = s3Client.listBuckets().buckets().stream()
                .anyMatch(bucket -> bucket.name().equals(bucketName));

        if(!exists) {
            try {
                s3Client.createBucket(CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .build());
            } catch (S3Exception s3Exception) {
                if (!s3Exception.awsErrorDetails().errorCode().equals("BucketAlreadyOwnedByYou")) {
                    throw s3Exception;
                }
            }
        }
    }



    public InputStream download(String bucketName, String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            return s3Client.getObject(getObjectRequest);
        } catch (S3Exception e) {
            throw new FileStorageException("Error downloading file", e);
        }

    }



    public String getContentType(String bucketName, String key) {
        try {
            HeadObjectRequest headRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            return s3Client.headObject(headRequest).contentType();
        } catch (NoSuchKeyException e) {
            throw new FileStorageException("File not found: " + key, e);
        } catch (S3Exception e) {
            throw new FileStorageException("Error retrieving metadata: " + e.getMessage(), e);
        }
    }



    public void delete(String bucketName, String key) {
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteRequest);
        } catch (S3Exception e) {
            throw new FileStorageException("Error deleting file: " + e.getMessage(), e);
        }
    }


    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

    private String buildFileName(Long userProfileId, MultipartFile file) {
        String extension = getFileExtension(file.getOriginalFilename());
        String uuid = UUID.randomUUID().toString();
        return userProfileId + "/" + uuid + extension;
    }
}
