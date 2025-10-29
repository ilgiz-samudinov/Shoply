package com.example.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientResourcesException extends ApplicationException{
    public InsufficientResourcesException(String message) {
        super(message);
    }
}
