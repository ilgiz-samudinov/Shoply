package com.example.paymentservice.service;

import com.example.paymentservice.dto.CardTokenRequest;
import com.example.paymentservice.dto.CardTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardTokenService {

    public CardTokenResponse generateToken(CardTokenRequest req) {
        // тут можно добавить валидацию PAN по Luhn, но для простоты опустим
        String token = "ct_" + UUID.randomUUID();
        long expiresAt = Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli(); // живёт 1 день
        return new CardTokenResponse(token, expiresAt);
    }


}
