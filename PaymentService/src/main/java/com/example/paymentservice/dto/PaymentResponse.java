package com.example.paymentservice.dto;

import com.example.paymentservice.model.Currency;
import com.example.paymentservice.model.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
@Data
public class PaymentResponse {
    private Long id;

    private Long userId;

    private BigDecimal amount;

    private PaymentStatus paymentStatus;

    private Currency currency = Currency.USD;

    private Instant createdAt;
    private Instant updatedAt;

}
