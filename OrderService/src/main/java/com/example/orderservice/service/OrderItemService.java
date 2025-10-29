package com.example.orderservice.service;



import com.example.orderservice.dto.OrderItemRequest;
import com.example.orderservice.dto.external.Product;
import com.example.orderservice.exception.NotFoundException;
import com.example.orderservice.mapper.OrderItemMapper;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderItem;
import com.example.orderservice.client.ProductServiceClient;
import com.example.orderservice.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final OrderItemMapper orderItemMapper;
    private final ProductServiceClient productServiceClient;

    @Transactional
    public OrderItem createOrderItem(OrderItemRequest orderItemRequest) {
        Order order = orderService.getOrderById(orderItemRequest.getOrderId());
        Product product = getProductById(orderItemRequest.getProductId());
        OrderItem orderItem  = orderItemMapper.toEntity(orderItemRequest, order);
        orderItem.setPrice(calculateTotalPrice(product.getPrice(), orderItem.getQuantity()));
        order.getItems().add(orderItem);
        orderService.calculateOrderTotal(order);
        return orderItemRepository.save(orderItem);
    }


    @Transactional(readOnly  = true)
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }



    @Transactional(readOnly = true)
    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElseThrow(() -> new NotFoundException("OrderItem not found"));
    }

    @Transactional
    public OrderItem updateOrderItemQuantity(Long id, Integer quantity) {
        OrderItem orderItem = getOrderItemById(id);
        Product product = getProductById(orderItem.getProductId());
        Double totalPrice = calculateTotalPrice(product.getPrice(), quantity);
        Order order = orderItem.getOrder();

        orderItem.setQuantity(quantity);
        orderItem.setPrice(totalPrice);
        orderService.calculateOrderTotal(order);

        return orderItemRepository.save(orderItem);
    }


    @Transactional
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = getOrderItemById(id);
        Order order = orderItem.getOrder();
        order.getItems().remove(orderItem);
        orderService.calculateOrderTotal(order);
    }


    public Product getProductById(Long id){
        Product product = productServiceClient.getProductById(id);
        if(product == null){
            throw new NotFoundException("Product not found");
        }
        return product;
    }


    private Double calculateTotalPrice (Double price, Integer quantity) {
        return price * quantity;
    }



}
