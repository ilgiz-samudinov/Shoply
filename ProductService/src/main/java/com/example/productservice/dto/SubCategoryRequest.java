package com.example.productservice.dto;

import lombok.Data;

@Data
public class SubCategoryRequest {
    private String name;
    private String slug;
    private Long categoryId;
}
