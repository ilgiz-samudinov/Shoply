package com.example.bankservice.dto.card;

import com.example.bankservice.model.enums.CardStatus;
import com.example.bankservice.model.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CardResponse {

    private Long id;
    private Long accountId;
    private String maskedPan;
    private YearMonth expiryDate;
    private String cvv;
    private CardType cardType;

    private CardStatus cardStatus;

}

