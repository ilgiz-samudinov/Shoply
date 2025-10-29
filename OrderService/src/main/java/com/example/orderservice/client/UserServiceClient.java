package com.example.orderservice.client;

import com.example.orderservice.dto.external.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient userWebClient;


    public User getUserById(Long id) {
        return userWebClient.get()
                .uri("/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}
