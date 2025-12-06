package com.example.productservice.service;

import com.example.productservice.dto.CategoryRequest;
import com.example.productservice.exception.NotFoundException;
import com.example.productservice.mapper.CategoryMapper;
import com.example.productservice.model.Category;
import com.example.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Transactional
    public Category createCategory(Category category) {
        if(category == null) {
            throw new NotFoundException("category request is null");
        }
        return categoryRepository.save(category);
    }


    @Transactional
    public Category updateCategory(Long id, Category category) {
        if(category == null) {
            throw new NotFoundException("category request is null");
        }
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(category.getName());
        existingCategory.setSlug(category.getSlug());
        return categoryRepository.save(existingCategory);
    }


    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("category not found"));
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Transactional
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new NotFoundException("category not found");
        }
        categoryRepository.deleteById(id);
    }
}
