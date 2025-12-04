package com.example.productservice.config;


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
    public Queue queue() {
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
    public DirectExchange directExchange() {
        return new DirectExchange(rabbitMqProperties.getExchange(), true, false);
    }



    @Bean
    public Binding dlqBinding(Queue deadLetterQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(directExchange)
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


    @Bean
    public Binding createBinding(Queue queue, DirectExchange exchange, RabbitMqProperties props) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(props.getRoutingKeyCreate());
    }

    @Bean
    public Binding updateBinding(Queue queue, DirectExchange exchange, RabbitMqProperties props) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(props.getRoutingKeyUpdate());
    }

    @Bean
    public Binding deleteBinding(Queue queue, DirectExchange exchange, RabbitMqProperties props) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(props.getRoutingKeyDelete());
    }
}
