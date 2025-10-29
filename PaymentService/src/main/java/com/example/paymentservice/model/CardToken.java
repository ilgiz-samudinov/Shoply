package com.example.paymentservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "card_tokens")
public class CardToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pan;

    @Column(nullable = false)
    private String expiryDate;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus = CardStatus.ACTIVE;

    @Column(updatable = false)
    private Instant createdAt;

    @Transient
    public String getMaskedPan() {
        if (pan == null || pan.length() < 6) return "****";
        String start = pan.substring(0, 6);
        String end = pan.substring(pan.length() - 4);
        return start + "******" + end;
    }
}
