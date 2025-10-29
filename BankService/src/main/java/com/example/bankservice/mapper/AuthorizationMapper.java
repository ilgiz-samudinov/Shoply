package com.example.bankservice.mapper;


import com.example.bankservice.dto.authorization.AuthorizationRequest;
import com.example.bankservice.dto.authorization.AuthorizationResponse;
import com.example.bankservice.model.Authorization;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AuthorizationMapper {
    Authorization toEntity(AuthorizationRequest authorizationRequest);
    AuthorizationResponse toResponse(Authorization authorization);


//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "id", ignore = true)
//    void merge(Authorization existing, Authorization authorization);
}
