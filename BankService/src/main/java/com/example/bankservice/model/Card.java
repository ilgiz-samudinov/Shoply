package com.example.bankservice.model;

import com.example.bankservice.model.enums.CardStatus;
import com.example.bankservice.model.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards", indexes = {
        @Index(name = "idx_card_account", columnList = "account_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, unique = true)
    private String pan;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;


    @Column(name = "expiry_date", nullable = false)
    private YearMonth expiryDate;


//    @Transient
    private String cvv;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_status", nullable = false)
    private CardStatus cardStatus;

    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Authorization> authorizations = new ArrayList<>();



    @PrePersist
    public void onCreate(){
        if(cardStatus == null) this.cardStatus = CardStatus.ACTIVE;
        if(cardType == null) this.cardType = CardType.DEBIT;
    }

    public void inactiveCard(){
        this.cardStatus = CardStatus.INACTIVE;
    }

    public void blockCard(){
        this.cardStatus = CardStatus.BLOCKED;
    }



}
