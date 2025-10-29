package com.example.bankservice.service;

import com.example.bankservice.dto.card.CardRequest;
import com.example.bankservice.exception.NotFoundException;
import com.example.bankservice.mapper.CardMapper;
import com.example.bankservice.model.Account;
import com.example.bankservice.model.Card;
import com.example.bankservice.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final AccountService accountService;

    @Transactional
    public Card createCard(CardRequest cardRequest){
        Account  account =  accountService.getAccount(cardRequest.getAccountId());
        Card card =  cardMapper.toEntity(cardRequest);
        card.setAccount(account);
        return cardRepository.save(card);
    }



    @Transactional(readOnly = true)
    public Card getCardById(Long id){
        return cardRepository.findById(id).orElseThrow(()-> new NotFoundException("Card not found"));
    }



    @Transactional(readOnly = true)
    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }


    @Transactional
    public void deleteCard(Long id){
        if(!cardRepository.existsById(id)){
            throw new NotFoundException("Card not found");
        }
        cardRepository.deleteById(id);
    }


    @Transactional
    public void inactiveCard(Long cardId){
        Card card = getCardById(cardId);
        card.inactiveCard();
        cardRepository.save(card);
    }



    @Transactional
    public void blockCard(Long cardId){
        Card card = getCardById(cardId);
        card.blockCard();
        cardRepository.save(card);
    }




}
