package com.example.bankservice.model;

import com.example.bankservice.exception.AccountNotActiveException;
import com.example.bankservice.exception.InsufficientFundsException;
import com.example.bankservice.model.enums.AccountStatus;
import com.example.bankservice.model.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts",
        indexes = {
                @Index(name = "idx_account_account_number", columnList = "account_number"),
                @Index(name = "idx_account_user_id", columnList = "user_id")
        })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "account_number", nullable = false, unique = true, length = 34)
    private String accountNumber;

    @Column(nullable = false)
    private long balance = 0L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;


    private long reserved = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Version
    private Long version;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY /*, cascade = CascadeType.ALL, orphanRemoval = true */)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Transaction> transactions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (currency == null) currency = Currency.USD;
        if (accountStatus == null) accountStatus = AccountStatus.ACTIVE;
    }

    public long available(){
        return balance - reserved;
    }


    public void reserve(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (available() >= amount) {
            this.reserved += amount;
        } else {
            throw new IllegalStateException("Insufficient available balance");
        }
    }


    public void release(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (this.reserved >= amount) {
            this.reserved -= amount;
        } else {
            throw new IllegalStateException("Not enough reserved funds to release");
        }
    }





    /**
     * Зачисление средств (credit).
     * @param amount сумма в минимальной единице валюты (центах), должна быть > 0
     */
    public void credit(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Credit amount must be positive");
        }
        // защита от переполнения
        if (balance > Long.MAX_VALUE - amount) {
            throw new ArithmeticException("Balance overflow");
        }
        this.balance += amount;

        // TODO: создать и добавить Transaction в transactions (если нужно)
        // transactions.add(Transaction.credit(this, amount, Instant.now()));
    }

    /**
     * Списание средств (debit).
     * @param amount сумма в минимальной единице валюты (центах), должна быть > 0
     */
    public void debit(long amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Debit amount must be positive");
        }
        if (this.balance < amount) {
            throw new InsufficientFundsException("Insufficient funds"); // рекомендую своё исключение
        }
        this.balance -= amount;

        // TODO: создать и добавить Transaction
        // transactions.add(Transaction.debit(this, amount, Instant.now()));
    }

    /**
     * Быстрая проверка статуса (не бросает исключений).
     */
    public boolean isActive() {
        return this.accountStatus == AccountStatus.ACTIVE;
    }

    /**
     * Гарантирует, что аккаунт активен, иначе бросает исключение.
     */
    public void ensureActive() {
        if (!isActive()) {
            throw new AccountNotActiveException("Account is not active"); // рекомендую своё исключение
        }
    }

    /**
     * Блокировка аккаунта (временная).
     * Переход: ACTIVE -> BLOCKED
     */
    public void block() {
        if (this.accountStatus == AccountStatus.ACTIVE) {
            this.accountStatus = AccountStatus.BLOCKED;
        }
    }

    /**
     * Деактивация / закрытие аккаунта (INACTIVE).
     * Переход: ACTIVE -> INACTIVE
     */
    public void deactivate() {
        if (this.accountStatus == AccountStatus.ACTIVE) {
            this.accountStatus = AccountStatus.INACTIVE;
        }
    }

    // Добавь (при необходимости) методы unblock() / reactivate() для возврата в ACTIVE
}
