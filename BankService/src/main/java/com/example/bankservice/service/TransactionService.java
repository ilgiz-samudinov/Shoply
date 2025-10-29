package com.example.bankservice.service;

import com.example.bankservice.dto.transaction.TransactionRequest;
import com.example.bankservice.model.Account;
import com.example.bankservice.model.Authorization;
import com.example.bankservice.model.Transaction;
import com.example.bankservice.model.enums.Currency;
import com.example.bankservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final Clock clock;
    private final AccountService accountService;
    private final AuthorizationService authorizationService;

    @Transactional
    public Transaction createTransaction(TransactionRequest transactionRequest) {
        Instant now = Instant.now(clock);
        Account account = accountService.getAccount(transactionRequest.getAccountId());
        accountService.debit(account.getId(), transactionRequest.getAmount());
        account.release(transactionRequest.getAmount());
        Authorization authorization = authorizationService.getAuthorizationById(transactionRequest.getAuthorizationId());
        Transaction transaction = Transaction.builder()
                .createdAt(now)
                .account(account)
                .authorization(authorization)
                .amount(transactionRequest.getAmount())
                .currency(Currency.USD)
                .build();

        transaction.succeeded();
        transaction.capture();


        return transactionRepository.save(transaction);
    }





//    public Transaction captureTransaction(){
//
//    }


    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
    }

    @Transactional
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }
}
