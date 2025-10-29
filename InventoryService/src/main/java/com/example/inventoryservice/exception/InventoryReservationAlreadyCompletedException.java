package com.example.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InventoryReservationAlreadyCompletedException extends ApplicationException{
    public InventoryReservationAlreadyCompletedException(String message) {
        super(message);
    }
}
