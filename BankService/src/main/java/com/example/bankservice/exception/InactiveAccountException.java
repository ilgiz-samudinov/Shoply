package com.example.bankservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InactiveAccountException extends ApplicationException {
    public InactiveAccountException(String message) {
        super(message);
    }
}


