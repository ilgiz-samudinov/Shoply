package com.example.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;

import java.io.InputStream;

@Getter
@Setter
@Builder
public class UserAvatarDownload {
    private InputStream inputStream;
    private String contentType;
    private MediaType mediaType;
    private String url;

}
