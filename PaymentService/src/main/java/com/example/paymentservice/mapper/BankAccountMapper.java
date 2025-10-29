package com.example.paymentservice.mapper;

import com.example.paymentservice.dto.BankAccountRequest;
import com.example.paymentservice.dto.BankAccountResponse;
import com.example.paymentservice.model.BankAccount;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccount toResponse(BankAccountRequest bankAccountRequest);

    BankAccountResponse toResponse(BankAccount bankAccount);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void merge(BankAccount existingBankAccount, BankAccount bankAccount);
}
