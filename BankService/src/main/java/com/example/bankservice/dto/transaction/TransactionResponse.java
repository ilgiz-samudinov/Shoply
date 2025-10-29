package com.example.bankservice.dto.transaction;


import com.example.bankservice.model.enums.Currency;
import com.example.bankservice.model.enums.TransactionStatus;
import com.example.bankservice.model.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@Builder
public class TransactionResponse {

    private Long id;
    private Long accountId;
    private Long authorizationId;
    private TransactionType transactionType;
    private long amount;
    private Currency currency;
    private TransactionStatus transactionStatus;
    private Instant createdAt;
}
