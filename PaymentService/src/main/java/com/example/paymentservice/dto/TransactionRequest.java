package com.example.paymentservice.dto;

import com.example.paymentservice.model.TransactionStatus;
import com.example.paymentservice.model.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionRequest {
    private Long  id;

    private Long authorizationId;
//    private Authorization authorization;


    private TransactionType transactionType;

    private BigDecimal amount;

    private String currency;

    private TransactionStatus status;
    private Instant createdAt ;
}
