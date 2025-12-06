package com.example.productservice.controller;

import com.example.productservice.dto.SubCategoryRequest;
import com.example.productservice.dto.SubCategoryResponse;
import com.example.productservice.mapper.SubCategoryMapper;
import com.example.productservice.model.SubCategory;
import com.example.productservice.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;
    private final SubCategoryMapper subCategoryMapper;

    @PostMapping
    public ResponseEntity<SubCategoryResponse> create(@RequestBody SubCategoryRequest request) {
        SubCategory subCategory = subCategoryMapper.toEntity(request);
        SubCategory created = subCategoryService.createSubCategory(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategoryMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubCategoryResponse> update(
            @PathVariable Long id,
            @RequestBody SubCategoryRequest request
    ) {
        SubCategory subCategory = subCategoryMapper.toEntity(request);
        SubCategory updated = subCategoryService.updateSubCategory(id, subCategory);
        return ResponseEntity.ok(subCategoryMapper.toResponse(updated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryResponse> getById(@PathVariable Long id) {
        SubCategory subCategory = subCategoryService.getSubCategory(id);
        return ResponseEntity.ok(subCategoryMapper.toResponse(subCategory));
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryResponse>> getAll() {
        List<SubCategory> list = subCategoryService.getAllSubCategories();
        List<SubCategoryResponse> response = list.stream()
                .map(subCategoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<SubCategoryResponse>> getByCategory(@PathVariable Long categoryId) {
        List<SubCategory> list = subCategoryService.getByCategoryId(categoryId);
        List<SubCategoryResponse> response = list.stream()
                .map(subCategoryMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subCategoryService.deleteSubCategory(id);
        return ResponseEntity.noContent().build();
    }
}
