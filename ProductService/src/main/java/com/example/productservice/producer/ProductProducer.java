package com.example.productservice.producer;

import com.example.productservice.config.RabbitMqProperties;
import com.example.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductProducer {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqProperties rabbitMqProperties;


    public void sendCreated(Product product) {
        rabbitTemplate.convertAndSend(rabbitMqProperties.getExchange(), rabbitMqProperties.getRoutingKeyCreate(), product);
    }

    public void sendUpdated(Product product) {
        rabbitTemplate.convertAndSend(rabbitMqProperties.getExchange(), rabbitMqProperties.getRoutingKeyUpdate(), product);
    }

    public void sendDeleted(Long id) {
        rabbitTemplate.convertAndSend(rabbitMqProperties.getExchange(), rabbitMqProperties.getRoutingKeyDelete(), id);
    }

}
