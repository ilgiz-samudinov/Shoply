package com.example.productservice.service;


import com.example.productservice.dto.ProductRequest;
import com.example.productservice.exception.NotFoundException;
import com.example.productservice.mapper.ProductMapper;
import com.example.productservice.model.Product;
import com.example.productservice.producer.ProductProducer;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductProducer productProducer;

    @Transactional
    public Product createProduct(Product product) {
         Product saved = productRepository.save(product);
         productProducer.sendCreated(saved);
         return saved;
    }



    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existing = getProductById(id);
        productMapper.merge(existing, product);
        productProducer.sendUpdated(existing);
        return existing;
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with id " + id));
    }


    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found with id " + id);
        }
        productRepository.deleteById(id);
        productProducer.sendDeleted(id);
    }


    @Transactional(readOnly = true)
    public Product getProductByTitle(String title){
        return productRepository.findProductByTitle(title)
                .orElseThrow(()-> new NotFoundException("Product with " + title + " not found"));
    }
}
