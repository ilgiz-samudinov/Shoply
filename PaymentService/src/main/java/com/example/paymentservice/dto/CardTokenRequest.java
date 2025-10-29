package com.example.paymentservice.dto;

import lombok.Data;
@Data
public class CardTokenRequest {
    private String pan;

    private String expiryDate;

    private String cvv;

}
