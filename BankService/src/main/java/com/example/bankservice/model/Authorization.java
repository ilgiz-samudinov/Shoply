package com.example.bankservice.model;

import com.example.bankservice.model.enums.AuthorizationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE) // чтобы нельзя было напрямую менять статус извне
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authorizations")
@Builder
public class Authorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private long amountCent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorizationStatus authorizationStatus;

    @OneToMany(mappedBy = "authorization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // -------------------------------
    // ⚙️ Доменные методы
    // -------------------------------

    public void authorize() {
        if (authorizationStatus != null && authorizationStatus != AuthorizationStatus.DECLINED && authorizationStatus != AuthorizationStatus.FAILED) {
            throw new IllegalStateException("Authorization can only be created in DECLINED or FAILED state.");
        }
        this.authorizationStatus = AuthorizationStatus.AUTHORIZED;
    }

    public void decline() {
        if (authorizationStatus == AuthorizationStatus.AUTHORIZED) {
            throw new IllegalStateException("Cannot decline an already authorized transaction.");
        }
        this.authorizationStatus = AuthorizationStatus.DECLINED;
    }

    public void voidAuthorization() {
        if (authorizationStatus != AuthorizationStatus.AUTHORIZED) {
            throw new IllegalStateException("Only an authorized transaction can be voided.");
        }
        this.authorizationStatus = AuthorizationStatus.VOIDED;
    }

    public void refund() {
        if (authorizationStatus != AuthorizationStatus.AUTHORIZED && authorizationStatus != AuthorizationStatus.VOIDED) {
            throw new IllegalStateException("Only authorized or voided transactions can be refunded.");
        }
        this.authorizationStatus = AuthorizationStatus.REFUNDED;
    }

    public void fail() {
        if (authorizationStatus == AuthorizationStatus.AUTHORIZED || authorizationStatus == AuthorizationStatus.REFUNDED) {
            throw new IllegalStateException("Cannot fail an authorized or refunded transaction.");
        }
        this.authorizationStatus = AuthorizationStatus.FAILED;
    }

    public boolean isAuthorized() {
        return this.authorizationStatus == AuthorizationStatus.AUTHORIZED;
    }

    public boolean isDeclined() {
        return this.authorizationStatus == AuthorizationStatus.DECLINED;
    }

    public boolean isRefunded() {
        return this.authorizationStatus == AuthorizationStatus.REFUNDED;
    }

    public boolean isFailed() {
        return this.authorizationStatus == AuthorizationStatus.FAILED;
    }

    public boolean isVoided() {
        return this.authorizationStatus == AuthorizationStatus.VOIDED;
    }
}
