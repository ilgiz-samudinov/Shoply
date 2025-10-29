package com.example.userservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FileDeleteEvent {
    private final String key;
    private final String bucketName;
}