package com.example.bankservice.controller;

import com.example.bankservice.dto.account.AccountResponse;
import com.example.bankservice.mapper.AccountMapper;
import com.example.bankservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.bankservice.model.Account;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/{userId}")
    public ResponseEntity<AccountResponse> createAccount(@PathVariable Long userId) {
        Account  account = accountService.createAccount(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountMapper.toResponse(account));
    }


    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
       return ResponseEntity.ok(accountService.getAllAccounts().stream().map(accountMapper::toResponse).toList());
    }



    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id){
        Account account = accountService.getAccount(id);
        return ResponseEntity.ok(accountMapper.toResponse(account));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }





    @PostMapping("/{accountId}/credit")
    public ResponseEntity<Long>  credit(@PathVariable Long accountId, @RequestParam ("amount") long amount){
        return ResponseEntity.ok(accountService.credit(accountId, amount));
    }

    @PostMapping("/{accountId}/debit")
    public ResponseEntity<Long>  debit(@PathVariable Long accountId, @RequestParam ("amount") long amount){
        return ResponseEntity.ok(accountService.debit(accountId, amount));
    }

}
