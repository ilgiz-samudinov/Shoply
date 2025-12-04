package com.example.productservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "productservice.rabbitmq")
@Getter @Setter
public class RabbitMqProperties {
    private String queue;
    private String dlq;
    private String exchange;
    private String routingKey;
    private String dlqRoutingKey;
    private String routingKeyCreate;
    private String routingKeyUpdate;
    private String routingKeyDelete;

}
