package com.example.bankservice.mapper;

import com.example.bankservice.dto.transaction.TransactionRequest;
import com.example.bankservice.dto.transaction.TransactionResponse;
import com.example.bankservice.model.Transaction;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toEntity(TransactionRequest transactionRequest);

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "authorizationId", source = "authorization.id")
    TransactionResponse toResponse(Transaction transaction);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void merge(@MappingTarget Transaction existing, Transaction transaction);

}
