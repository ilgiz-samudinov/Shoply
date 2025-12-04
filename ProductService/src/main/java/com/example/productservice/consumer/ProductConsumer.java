package com.example.productservice.consumer;

import com.example.productservice.model.Product;
import com.example.productservice.service.ProductDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@RabbitListener(queues = "#{rabbitMqProperties.queue}")
public class ProductConsumer {

    private final ProductDocumentService productDocumentService;

    @RabbitHandler
    public void handleProduct(Product product,
                              @Header("amqp_receivedRoutingKey") String routingKey) {
        if ("product.created".equals(routingKey) || "product.updated".equals(routingKey)) {
            productDocumentService.createOrUpdateProductDocument(product);
        }
    }

    @RabbitHandler
    public void handleDeleted(Long productId, @Header("amqp_receivedRoutingKey") String routingKey) {
        if ("product.deleted".equals(routingKey)) {
            productDocumentService.deleteProductDocument(productId.toString());
        }
    }
}
