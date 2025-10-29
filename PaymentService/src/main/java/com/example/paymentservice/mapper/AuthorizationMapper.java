package com.example.paymentservice.mapper;

import com.example.paymentservice.dto.AuthorizationRequest;
import com.example.paymentservice.dto.AuthorizationResponse;
import com.example.paymentservice.model.Authorization;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AuthorizationMapper {
    Authorization toEntity(AuthorizationRequest authorizationRequest);
    AuthorizationResponse toResponse(Authorization authorization);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void merge(Authorization existing, Authorization authorization);
}
