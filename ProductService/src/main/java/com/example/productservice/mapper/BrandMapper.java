package com.example.productservice.mapper;

import com.example.productservice.dto.BrandRequest;
import com.example.productservice.dto.BrandResponse;
import com.example.productservice.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toEntity(BrandRequest brandRequest);
    BrandResponse toResponse(Brand brand);
}
