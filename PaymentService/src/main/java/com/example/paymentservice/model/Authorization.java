package com.example.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authorizations")
public class Authorization {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long card_token_id;

    @Column(nullable = false)
    private Long merchant_order_id;


    @Column(nullable = false)
    private long amountCent;

    private long refundedAmountCent = 0;


    @OneToMany(mappedBy = "authorization")
    private List<Transaction> transactions;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorizationStatus status;


    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt;
}
