package com.example.bankservice.controller;

import com.example.bankservice.dto.card.CardRequest;
import com.example.bankservice.dto.card.CardResponse;
import com.example.bankservice.mapper.CardMapper;
import com.example.bankservice.model.Card;
import com.example.bankservice.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService;
    private final CardMapper cardMapper;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(@RequestBody CardRequest cardRequest) {
        Card card =  cardService.createCard(cardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardMapper.toResponse(card));
    }


    @GetMapping
    public ResponseEntity<List<CardResponse>> getAllCards(){
        return ResponseEntity.ok(cardService.getAllCards().stream().map(cardMapper::toResponse).toList());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCard(@PathVariable Long id){
        Card card = cardService.getCardById(id);
        return ResponseEntity.ok(cardMapper.toResponse(card));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id){
        cardService.deleteCard(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PatchMapping("/{id}/inactive")
    public ResponseEntity<Void> inactiveCard(@PathVariable Long id){
        cardService.inactiveCard(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @PatchMapping("/{id}/block")
    public ResponseEntity<Void> blockCard(@PathVariable Long id){
        cardService.blockCard(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
