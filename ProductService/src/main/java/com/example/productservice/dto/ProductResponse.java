package com.example.productservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long id;
    private String title;

    private String description;

    private BigDecimal price;

}
