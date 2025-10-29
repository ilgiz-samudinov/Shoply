package com.example.paymentservice.dto;

import com.example.paymentservice.model.AuthorizationStatus;
import lombok.Data;

@Data
public class AuthorizationRequest {

    private Long card_token_id;

    private Long merchant_order_id;

    private long amountCent;

    private long refundedAmountCent = 0;

    private AuthorizationStatus status;

}
