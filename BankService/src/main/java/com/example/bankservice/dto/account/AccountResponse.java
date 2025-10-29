package com.example.bankservice.dto.account;

import com.example.bankservice.model.enums.AccountStatus;
import com.example.bankservice.model.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
@Getter
@Setter
public class AccountResponse {

    private Long id;

    private Long userId;

    private String accountNumber;

    private long balance;

    private long reserved;

    private Currency currency;

    private AccountStatus accountStatus;

    private Instant createdAt;

}
