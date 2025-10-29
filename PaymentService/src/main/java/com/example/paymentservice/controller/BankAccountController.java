package com.example.paymentservice.controller;

import com.example.paymentservice.dto.BankAccountResponse;
import com.example.paymentservice.mapper.BankAccountMapper;
import com.example.paymentservice.model.BankAccount;
import com.example.paymentservice.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @PostMapping("/{userId}")
    public ResponseEntity<BankAccountResponse> createBankAccount(@PathVariable Long userId) {
        BankAccount created = bankAccountService.createBankAccount(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountMapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountResponse> getBankAccount(@PathVariable Long id) {
        BankAccount bankAccount = bankAccountService.getBankAccount(id);
        return ResponseEntity.ok(bankAccountMapper.toResponse(bankAccount));
    }

    @GetMapping
    public ResponseEntity<List<BankAccountResponse>> getBankAccounts() {
        List<BankAccount> accounts = bankAccountService.getBankAccounts();
        return ResponseEntity.ok(accounts.stream()
                .map(bankAccountMapper::toResponse)
                .toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }
}
