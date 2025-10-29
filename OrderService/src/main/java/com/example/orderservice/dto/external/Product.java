package com.example.orderservice.dto.external;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String title;

    private String description;

    private Double price;

    private Integer quantity;
}
