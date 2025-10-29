package com.example.bankservice.service;

import com.example.bankservice.client.UserServiceClient;
import com.example.bankservice.dto.external.User;
import com.example.bankservice.exception.NotFoundException;
import com.example.bankservice.model.Account;
import com.example.bankservice.model.enums.AccountStatus;
import com.example.bankservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserServiceClient userServiceClient;
    private final Clock clock;
    private final AccountNumberGenerator accountNumberGenerator;


    private static final int RETRY_COUNT = 3;

    @Transactional
    public Account createAccount(Long userId){
        Instant now = Instant.now(clock);
        User user = getUserById(userId);
        Account account = Account.builder()
                .accountNumber(accountNumberGenerator.generateUniqueAccountNumber())
                .userId(user.getId())
                .createdAt(now)
                .build();
        return accountRepository.save(account);
    }

    @Transactional(readOnly  = true)
    public Account getAccount(Long id){
        return accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Transactional(readOnly  = true)
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    @Transactional
    public void deleteAccount(Long id){
        if(!accountRepository.existsById(id)){
            throw new NotFoundException("Account not found");
        }
        accountRepository.deleteById(id);
    }

    /**
     * Зачисление (credit). Возвращает обновлённый баланс.
     */
    @Transactional
    public long credit(Long accountId, long amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        int attempts = 0;
        while (true) {
            try {
                Account account = getAccount(accountId);

                ensureActive(account);

                // проверка переполнения баланса
                if (account.getBalance() > Long.MAX_VALUE - amount) {
                    throw new ArithmeticException("Balance overflow");
                }

                account.credit(amount);
                accountRepository.save(account); // для явности
                return account.getBalance();
            } catch (OptimisticLockingFailureException e) {
                if (++attempts >= RETRY_COUNT) {
                    throw e;
                }
                // иначе повторяем (перечитаем свежие данные и попробуем снова)
            }
        }
    }

    /**
     * Списание (debit). Возвращает обновлённый баланс.
     */
    @Transactional
    public long debit(Long accountId, long amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        int attempts = 0;
        while (true) {
            try {
                Account account = getAccount(accountId);

                ensureActive(account);

                if (account.getBalance() < amount) {
                    throw new IllegalStateException("Insufficient funds");
                    // можно заменить на кастомное исключение: InsufficientFundsException
                }

                account.debit(amount);
                accountRepository.save(account);
                return account.getBalance();
            } catch (OptimisticLockingFailureException e) {
                if (++attempts >= RETRY_COUNT) {
                    throw e;
                }
                // retry
            }
        }
    }

//    public Long authorizeHold(long amount, Long accountId){
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Amount must be positive");
//        }
//
//        Account account = getAccount(accountId);
//        account.reserve(amount);
//        return amount;
//    }







    private User getUserById(Long userId){
        User user = userServiceClient.getUserById(userId);
        if(user == null){
            throw new NotFoundException("User not found");
        }
        return user;
    }

    private void ensureActive(Account account) {
        if (account.getAccountStatus() != AccountStatus.ACTIVE) {
            // рекомендую создать и использовать AccountNotActiveException
            throw new IllegalStateException("Account is not active");
        }
    }
}
