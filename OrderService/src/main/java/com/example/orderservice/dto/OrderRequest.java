package com.example.orderservice.dto;


import com.example.orderservice.model.OrderStatus;
import lombok.Data;

@Data
public class OrderRequest {
    private Long userId;
    private OrderStatus orderStatus;
}
