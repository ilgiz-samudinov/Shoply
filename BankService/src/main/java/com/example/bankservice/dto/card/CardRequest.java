package com.example.bankservice.dto.card;


import com.example.bankservice.model.enums.CardType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

/**
 * DTO для запроса создания карты.
 */
@Getter
@Setter
public class CardRequest {

    @NotNull(message = "Account ID is required")
    private Long accountId;

//    @NotNull(message = "Card type is required")
//    private CardType cardType;


    @NotNull(message = "pan is required")
    private String pan;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private YearMonth expiryDate;

    @NotNull(message = "CVV is required")
    private String cvv;
}
