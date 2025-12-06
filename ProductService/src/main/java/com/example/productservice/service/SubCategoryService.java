package com.example.productservice.service;

import com.example.productservice.dto.SubCategoryRequest;
import com.example.productservice.exception.NotFoundException;
import com.example.productservice.mapper.SubCategoryMapper;
import com.example.productservice.model.Category;
import com.example.productservice.model.SubCategory;
import com.example.productservice.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryService categoryService;
    private final SubCategoryMapper subCategoryMapper;

    @Transactional
    public SubCategory createSubCategory(SubCategory subCategory) {
        if (subCategory == null) {
            throw new NotFoundException("subCategory request is null");
        }

        Category category = categoryService.getCategoryById(subCategory.getCategory().getId());
        subCategory.setCategory(category);
        return subCategoryRepository.save(subCategory);
    }


    @Transactional
    public SubCategory updateSubCategory(Long id, SubCategory subCategory) {
        if (subCategory == null) {
            throw new NotFoundException("subCategory request is null");
        }

        SubCategory existing = getSubCategory(id);
        existing.setName(subCategory.getName());
        existing.setSlug(subCategory.getSlug());

        if (subCategory.getCategory().getId() != null && !subCategory.getCategory().getId().equals(existing.getCategory().getId())) {
            Category category = categoryService.getCategoryById(subCategory.getCategory().getId());
            existing.setCategory(category);
        }

        return subCategoryRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public SubCategory getSubCategory(Long id) {
        return subCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("subCategory not found"));
    }

    @Transactional(readOnly = true)
    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    @Transactional
    public void deleteSubCategory(Long id) {
        if (!subCategoryRepository.existsById(id)) {
            throw new NotFoundException("subCategory not found");
        }
        subCategoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<SubCategory> getByCategoryId(Long categoryId) {
        return subCategoryRepository.findByCategoryId(categoryId);
    }
}
