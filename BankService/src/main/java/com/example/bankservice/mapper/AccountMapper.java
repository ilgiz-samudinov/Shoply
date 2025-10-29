package com.example.bankservice.mapper;

import com.example.bankservice.dto.account.AccountResponse;
import com.example.bankservice.model.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {
//    Account toEntity(AccountRequest accountRequest);

    AccountResponse toResponse(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void merge(@MappingTarget Account existingAccount, Account account);
}
