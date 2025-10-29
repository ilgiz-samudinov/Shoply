package com.example.paymentservice.repository;

import com.example.paymentservice.model.CardToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTokenRepository extends JpaRepository<CardToken, Long> {
}
