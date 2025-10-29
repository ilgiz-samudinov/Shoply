package com.example.bankservice.dto.authorization;


import lombok.Data;

@Data
public class AuthorizationRequest {

    private Long cardId;

    private long amountCent;

}
