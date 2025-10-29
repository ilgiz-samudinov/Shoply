package com.example.paymentservice.service;

import com.example.paymentservice.dto.external.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @RabbitListener(queues = "order-created-queue")
    public void consume(Order order) {
        System.out.println("Order Consumed: " + order.toString());
    }
}
                                                                                                                                                                                                                                                                