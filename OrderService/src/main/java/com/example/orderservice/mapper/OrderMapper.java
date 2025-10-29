package com.example.orderservice.mapper;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.model.Order;
import org.mapstruct.*;

@Mapper(componentModel  = "spring")
public interface OrderMapper {

    //    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    Order toEntity (OrderRequest orderRequest);

    //    @Mapping(target  = "userId", source = "user.id")
    OrderResponse toResponse (Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "items", ignore = true)
    void merge (@MappingTarget Order existing, Order updated);
}
