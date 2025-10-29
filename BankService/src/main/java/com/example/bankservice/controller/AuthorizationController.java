package com.example.bankservice.controller;

import com.example.bankservice.dto.authorization.AuthorizationRequest;
import com.example.bankservice.dto.authorization.AuthorizationResponse;
import com.example.bankservice.model.Authorization;
import com.example.bankservice.model.enums.AuthorizationStatus;
import com.example.bankservice.service.AuthorizationService;
import com.example.bankservice.mapper.AuthorizationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authorizations")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;
    private final AuthorizationMapper authorizationMapper;

    @PostMapping
    public ResponseEntity<AuthorizationResponse> createAuthorization(
            @RequestBody AuthorizationRequest request) {
        Authorization authorization = authorizationService.createAuthorization(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorizationMapper.toResponse(authorization));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorizationResponse> getAuthorization(@PathVariable Long id) {
        Authorization authorization = authorizationService.getAuthorizationById(id);
        return ResponseEntity.ok(authorizationMapper.toResponse(authorization));
    }

    @GetMapping
    public ResponseEntity<List<AuthorizationResponse>> getAllAuthorizations() {
        List<AuthorizationResponse> responses = authorizationService.getAllAuthorizations()
                .stream()
                .map(authorizationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthorization(@PathVariable Long id) {
        authorizationService.deleteAuthorization(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<List<AuthorizationResponse>> getByCardId(@PathVariable Long cardId) {
        List<AuthorizationResponse> responses = authorizationService.findByCardId(cardId)
                .stream()
                .map(authorizationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AuthorizationResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam AuthorizationStatus status) {
        Authorization updated = authorizationService.changeStatus(id, status);
        return ResponseEntity.ok(authorizationMapper.toResponse(updated));
    }
}
