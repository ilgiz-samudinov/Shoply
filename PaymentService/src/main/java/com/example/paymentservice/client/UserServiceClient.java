package com.example.paymentservice.client;

import com.example.paymentservice.dto.external.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Component
@RequiredArgsConstructor
public class UserServiceClient {
    private final WebClient userWebClient;

    public User getUserById(Long id){
        return userWebClient.get()
                .uri("/{id}" , id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }
}
