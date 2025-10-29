package com.example.inventoryservice.client;


import com.example.inventoryservice.dto.external.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
@RequiredArgsConstructor
public class ProductServiceClient {

    private final WebClient productWebClient;

    public Product getProductById(Long id) {
        return productWebClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }
}


