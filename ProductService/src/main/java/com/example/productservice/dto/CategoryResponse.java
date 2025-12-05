package com.example.productservice.dto;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;

//    private List<SubCategory> subCategories;
}
