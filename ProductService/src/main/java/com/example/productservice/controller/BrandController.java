package com.example.productservice.controller;

import com.example.productservice.dto.BrandRequest;
import com.example.productservice.dto.BrandResponse;
import com.example.productservice.mapper.BrandMapper;
import com.example.productservice.model.Brand;
import com.example.productservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brands")

public class BrandController {

    private final BrandMapper brandMapper;
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@RequestBody BrandRequest brandRequest) {
        Brand brand = brandMapper.toEntity(brandRequest);
        Brand created = brandService.createBrand(brand);
        return ResponseEntity.status(HttpStatus.CREATED).body(brandMapper.toResponse(created));
    }


    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands(){
        return ResponseEntity.ok(
                brandService.getAllBrands().stream().map(brandMapper::toResponse).toList()
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long id,
                                                     @RequestBody BrandRequest brandRequest) {
        Brand brand = brandMapper.toEntity(brandRequest);
        Brand updated = brandService.updateBrand(id, brand);
        return ResponseEntity.ok(brandMapper.toResponse(updated));
    }


    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long id) {
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brandMapper.toResponse(brand));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok().build();
    }

}
