package com.example.productservice.mapper;

import com.example.productservice.dto.SubCategoryRequest;
import com.example.productservice.dto.SubCategoryResponse;
import com.example.productservice.model.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
    SubCategory toEntity(SubCategoryRequest subCategoryRequest);

    @Mapping(target = "categoryId", source = "category.id")
    SubCategoryResponse toResponse(SubCategory subCategory);
}
