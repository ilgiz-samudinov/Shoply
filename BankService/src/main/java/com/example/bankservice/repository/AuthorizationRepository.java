package com.example.bankservice.repository;



import com.example.bankservice.model.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
    List<Authorization> findByCardId(Long cardId);
}


