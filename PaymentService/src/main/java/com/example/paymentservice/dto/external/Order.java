package com.example.paymentservice.dto.external;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

    private Long id;
    private Long userId;
    private String orderStatus;

    private Double totalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
