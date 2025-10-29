package com.example.paymentservice.dto;

import lombok.Data;

import java.time.Instant;
@Data
public class BankAccountResponse {
    private Long id;

    private Long userId;

    private String accountHolderName;

    private Instant createdAt;
}
