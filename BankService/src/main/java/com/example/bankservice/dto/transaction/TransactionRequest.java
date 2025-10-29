package com.example.bankservice.dto.transaction;



import com.example.bankservice.model.enums.Currency;
import com.example.bankservice.model.enums.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для создания транзакции.
 */
@Getter
@Setter
public class TransactionRequest {

    @NotNull(message = "Account ID is required")
    private Long accountId;

    private Long authorizationId;


    @Min(value = 1, message = "Amount must be positive")
    private long amount;

//    @NotNull(message = "Currency is required")
//    private Currency currency;
}
