package com.example.bankservice.service;

import com.example.bankservice.dto.authorization.AuthorizationRequest;
import com.example.bankservice.model.Account;
import com.example.bankservice.model.Authorization;
import com.example.bankservice.model.enums.AuthorizationStatus;
import com.example.bankservice.model.Card;
import com.example.bankservice.repository.AuthorizationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final AuthorizationRepository authorizationRepository;
    private final CardService cardService;
    private final Clock clock;
    private final AccountService accountService;

    @Transactional
    public Authorization createAuthorization(AuthorizationRequest authorizationRequest) {
        Instant now  = Instant.now(clock);
        Card card = cardService.getCardById(authorizationRequest.getCardId());
        Account account = accountService.getAccount(card.getAccount().getId());

        account.reserve(authorizationRequest.getAmountCent());

        Authorization authorization = Authorization.builder()
                .card(card)
                .createdAt(now)
                .amountCent(authorizationRequest.getAmountCent())
                .authorizationStatus(AuthorizationStatus.AUTHORIZED)
                .build();

        return authorizationRepository.save(authorization);
    }

    @Transactional(readOnly = true)
    public Authorization getAuthorizationById(Long id) {
        return authorizationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Authorization not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Authorization> getAllAuthorizations() {
        return authorizationRepository.findAll();
    }


    @Transactional
    public void deleteAuthorization(Long id) {
        Authorization authorization = getAuthorizationById(id);
        authorizationRepository.delete(authorization);
    }

    @Transactional(readOnly = true)
    public List<Authorization> findByCardId(Long cardId) {
        return authorizationRepository.findByCardId(cardId);
    }

    /**
     * Смена статуса через доменные методы Authorization (authorize/decline/voidAuthorization/refund/fail).
     * Это безопаснее, чем прямой setAuthorizationStatus.
     */
    @Transactional
    public Authorization changeStatus(Long id, AuthorizationStatus targetStatus) {
        Authorization authorization = getAuthorizationById(id);

        switch (targetStatus) {
            case AUTHORIZED -> authorization.authorize();
            case DECLINED -> authorization.decline();
            case VOIDED -> authorization.voidAuthorization();
            case REFUNDED -> authorization.refund();
            case FAILED -> authorization.fail();
            default -> throw new IllegalArgumentException("Unknown target status: " + targetStatus);
        }

        return authorizationRepository.save(authorization);
    }
}
