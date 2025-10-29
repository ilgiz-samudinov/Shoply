package com.example.paymentservice.service;

import com.example.paymentservice.client.UserServiceClient;
import com.example.paymentservice.dto.external.User;
import com.example.paymentservice.exception.NotFoundException;
import com.example.paymentservice.model.BankAccount;
import com.example.paymentservice.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserServiceClient userServiceClient;
    private final Clock clock;

    @Transactional
    public BankAccount createBankAccount(Long userId) {
        Instant now = Instant.now(clock);
        User user = getUserById(userId);
        BankAccount bankAccount = BankAccount.builder()
                .accountHolderName(user.getFullName())
                .userId(userId)
                .createdAt(now)
                .build();
        return bankAccountRepository.save(bankAccount);
    }



    @Transactional(readOnly = true)
    public BankAccount getBankAccount(Long id) {
        return bankAccountRepository.findById(id).orElseThrow(() -> new NotFoundException("Bank account not found"));
    }


    @Transactional(readOnly = true)
    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Transactional
    public void deleteBankAccount(Long id) {
        if(!bankAccountRepository.existsById(id)) {
            throw new NotFoundException("Bank account not found");
        }
        bankAccountRepository.deleteById(id);
    }



    private User getUserById(Long userId){
        User user = userServiceClient.getUserById(userId);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        return user;
    }
}
