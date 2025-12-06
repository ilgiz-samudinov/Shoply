package com.example.productservice.service;

import com.example.productservice.exception.NotFoundException;
import com.example.productservice.model.Brand;
import com.example.productservice.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;


    @Transactional
    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }


    @Transactional(readOnly = true)
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand not found"));
    }


    @Transactional
    public Brand updateBrand(Long id, Brand brand) {
        Brand existing = getBrandById(id);
        existing.setName(brand.getName());
        existing.setSlug(brand.getSlug());
        return brandRepository.save(existing);
    }

    @Transactional
    public void deleteBrand(Long id) {
        if(!brandRepository.existsById(id)) {
          throw new NotFoundException("Brand not found");
        }
        brandRepository.deleteById(id);
    }
}
