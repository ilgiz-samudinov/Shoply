package com.example.orderservice.dto;

import com.example.orderservice.model.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Long userId;
    private OrderStatus orderStatus;

    private Double totalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
