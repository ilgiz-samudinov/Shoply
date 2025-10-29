package com.example.orderservice.producer;


import com.example.orderservice.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;

    public void send(OrderResponse orderResponse){
        rabbitTemplate.convertAndSend("order-created-exchange", "order-created-routing-key", orderResponse);
    }
}
