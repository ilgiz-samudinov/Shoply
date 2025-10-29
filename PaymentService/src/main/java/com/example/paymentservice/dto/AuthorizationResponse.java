package com.example.paymentservice.dto;

import com.example.paymentservice.model.AuthorizationStatus;
import com.example.paymentservice.model.Transaction;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class AuthorizationResponse {

    private Long id;

    private Long card_token_id;

    private Long merchant_order_id;

    private long amountCent;

    private long refundedAmountCent = 0;

    private List<Transaction> transactions;

    private AuthorizationStatus status;

    private Instant createdAt;
}
