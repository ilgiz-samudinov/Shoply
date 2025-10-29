package com.example.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class FileStorageException extends ApplicationException {
    public FileStorageException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}