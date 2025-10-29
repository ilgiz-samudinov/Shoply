package com.example.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotBlank(message = "Title is required")  // не пустая строка и не null
    private String title;

    @NotBlank(message = "Description is required")  // не пустая строка и не null
    private String description;

    @NotNull(message = "Price is required")       // не null
    @Min(value = 0, message = "Price must be >= 0") // минимальное значение
    private BigDecimal price;
}
