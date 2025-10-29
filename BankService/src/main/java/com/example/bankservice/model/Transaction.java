package com.example.bankservice.model;

import com.example.bankservice.model.enums.Currency;
import com.example.bankservice.model.enums.TransactionStatus;
import com.example.bankservice.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "transactions", indexes = {
        @Index(name = "idx_tx_account", columnList = "account_id"),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorization_id")
    private Authorization authorization;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false, precision = 19, scale = 2)
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus transactionStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;


    public void pending(){
        this.transactionStatus = TransactionStatus.PENDING;
    }

    public void succeeded(){
        this.transactionStatus = TransactionStatus.SUCCEEDED;
    }

    public void failed(){
        this.transactionStatus = TransactionStatus.FAILED;
    }


    public void capture(){
        this.transactionType = TransactionType.CAPTURE;
    }


    public void refunded(){
        this.transactionType = TransactionType.REFUND;
    }


    public void voided (){
        this.transactionType = TransactionType.VOID;
    }


}
