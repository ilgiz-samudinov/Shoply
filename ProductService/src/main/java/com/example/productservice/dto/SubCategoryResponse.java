package com.example.productservice.dto;

import lombok.Data;

@Data
public class SubCategoryResponse {
    private Long id;
    private String name;
    private String slug;

    private Long categoryId;
}
