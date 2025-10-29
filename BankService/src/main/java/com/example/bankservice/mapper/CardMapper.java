package com.example.bankservice.mapper;

import com.example.bankservice.dto.card.CardRequest;
import com.example.bankservice.dto.card.CardResponse;
import com.example.bankservice.model.Card;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CardMapper {

    Card toEntity(CardRequest cardRequest);

    default CardResponse toResponse(Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .accountId(card.getAccount().getId())
                .maskedPan(maskPan(card.getPan()))
                .expiryDate(card.getExpiryDate())
                .cardType(card.getCardType())
                .cardStatus(card.getCardStatus())
                .build();
    }

    default String maskPan(String pan) {
        if (pan == null || pan.length() < 4) return "****";
        return "**** **** **** " + pan.substring(pan.length() - 4);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void merge(@MappingTarget Card existing, Card card);
}
