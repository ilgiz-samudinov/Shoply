package com.example.inventoryservice.client;

import com.example.inventoryservice.dto.external.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderServiceClient {
    private final WebClient orderWebClient;

    public Order getOrderById(Long id){
        return orderWebClient.get()
                .uri("/{id}" , id)
                .retrieve()
                .bodyToMono(Order.class)
                .block();
    }

}
