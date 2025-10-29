package com.example.bankservice.dto.authorization;


import com.example.bankservice.model.enums.AuthorizationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
public class AuthorizationResponse {

    private Long id;
    private Long cardId;      //TODO нужна вернуть  cardId
    private long amountCent;
    private AuthorizationStatus authorizationStatus;
    private Instant createdAt;
}
