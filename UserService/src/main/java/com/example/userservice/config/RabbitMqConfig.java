package com.example.userservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final RabbitMqProperties rabbitMqProperties;

    @Bean
    public Queue usersQueue() {
        return QueueBuilder.durable(rabbitMqProperties.getQueue())
                .withArgument("x-dead-letter-exchange", rabbitMqProperties.getExchange())
                .withArgument("x-dead-letter-routing-key", rabbitMqProperties.getDlqRoutingKey())
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(rabbitMqProperties.getDlq()).build();
    }

    @Bean
    public DirectExchange usersExchange() {
        return new DirectExchange(rabbitMqProperties.getExchange(), true, false);
    }

    @Bean
    public Binding usersQueueBinding(Queue usersQueue, DirectExchange usersExchange) {
        return BindingBuilder.bind(usersQueue)
                .to(usersExchange)
                .with(rabbitMqProperties.getRoutingKey());
    }

    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, DirectExchange usersExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(usersExchange)
                .with(rabbitMqProperties.getDlqRoutingKey());
    }



    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }




}
