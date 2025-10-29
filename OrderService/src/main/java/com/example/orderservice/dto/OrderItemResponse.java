package com.example.orderservice.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double price;
}
