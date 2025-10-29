//package com.example.paymentservice.mapper;
//
//import com.example.paymentservice.dto.CardTokenRequest;
//import com.example.paymentservice.dto.CardTokenResponse;
//import com.example.paymentservice.model.CardToken;
//import org.mapstruct.BeanMapping;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.NullValuePropertyMappingStrategy;
//
//@Mapper(componentModel = "spring")
//public interface CardTokenMapper {
//    CardToken toEntity(CardTokenRequest cardTokenRequest);
//    CardTokenResponse toResponse(CardToken cardToken);
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "id", ignore = true)
//    void merge(CardToken existingCardToken, CardToken cardToken);
//}
