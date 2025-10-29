package com.example.paymentservice.controller;




import com.example.paymentservice.dto.CardTokenRequest;
import com.example.paymentservice.dto.CardTokenResponse;
import com.example.paymentservice.service.CardTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tokens")
@RequiredArgsConstructor
public class CardTokenController {

    private final CardTokenService service;

    @PostMapping
    public ResponseEntity<CardTokenResponse> createToken(@RequestBody CardTokenRequest req) {
        CardTokenResponse token = service.generateToken(req);
        return ResponseEntity.ok(token);
    }
}
