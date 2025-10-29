package com.example.orderservice.service;

import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderResponse;
import com.example.orderservice.dto.external.User;
import com.example.orderservice.exception.NotFoundException;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.producer.OrderProducer;
import com.example.orderservice.client.UserServiceClient;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserServiceClient userServiceClient;


    private final OrderProducer orderProducer;

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        User user = getUserById(orderRequest.getUserId());
        System.out.println("Полученный пользователь:  " + user.toString());
        Order order = orderMapper.toEntity(orderRequest);
        Order saved = orderRepository.save(order);
        return saved;
    }


    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Transactional
    public Order updateOrder(Long id, OrderRequest orderRequest) {
        User user = getUserById(id);
        Order existingOrder = getOrderById(id);
        Order order = orderMapper.toEntity(orderRequest);
        orderMapper.merge(existingOrder, order);
        return orderRepository.save(existingOrder);
    }

    @Transactional
    public void deleteOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new NotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
    }


    @Transactional
    public void calculateOrderTotal(Order order) {
        double total = order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);

    }


    @Transactional
    public void finalizeOrder(Long orderId) {
        Order order = getOrderById(orderId);
        calculateOrderTotal(order);
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        OrderResponse orderResponse = orderMapper.toResponse(order);
        if (order.getTotalAmount() > 0) {
            orderProducer.send(orderResponse);
        }
    }


    public User getUserById(Long id) {
        User user = userServiceClient.getUserById(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }

}

