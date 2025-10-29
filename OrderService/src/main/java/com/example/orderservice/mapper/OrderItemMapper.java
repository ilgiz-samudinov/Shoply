package com.example.orderservice.mapper;




import com.example.orderservice.dto.OrderItemRequest;
import com.example.orderservice.dto.OrderItemResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItem;
import org.mapstruct.*;

@Mapper(componentModel  = "spring")
public interface OrderItemMapper {

    @Mapping(target = "order", source = "order")
    @Mapping(target = "quantity", source = "orderItemRequest.quantity")
    @Mapping(target = "id", ignore = true)
    OrderItem toEntity(OrderItemRequest orderItemRequest, Order order);

    @Mapping(target = "orderId", source = "order.id")
    OrderItemResponse toResponse(OrderItem orderItem);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "productId", ignore = true)
    void merge(@MappingTarget OrderItem existing, OrderItem updated);

}
