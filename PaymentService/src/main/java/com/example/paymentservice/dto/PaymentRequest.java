package com.example.paymentservice.dto;

import com.example.paymentservice.model.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class PaymentRequest {

    private Long userId;

    private BigDecimal amount;

    private PaymentStatus paymentStatus;
}
