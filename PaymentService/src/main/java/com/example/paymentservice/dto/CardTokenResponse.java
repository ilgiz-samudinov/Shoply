package com.example.paymentservice.dto;

import lombok.Value;

@Value
public class CardTokenResponse {
    String token;      // ct_<uuid>
    long expiresAt;    // время жизни токена (мс epoch)
}