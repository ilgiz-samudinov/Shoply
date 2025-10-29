package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderItemRequest;
import com.example.orderservice.dto.OrderItemResponse;
import com.example.orderservice.mapper.OrderItemMapper;
import com.example.orderservice.model.OrderItem;
import com.example.orderservice.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;


    @PostMapping
    public ResponseEntity<OrderItemResponse> createOrderItem(@Valid @RequestBody OrderItemRequest orderItemRequest) {
        OrderItem orderItem = orderItemService.createOrderItem(orderItemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemMapper.toResponse(orderItem));
    }


    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAllOrderItems() {
        List<OrderItemResponse> responses = orderItemService.getAllOrderItems()
                .stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItemMapper.toResponse(orderItem));
    }


    @PutMapping("/{id}/quantity")
    public ResponseEntity<OrderItemResponse> updateOrderItemQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        OrderItem orderItem = orderItemService.updateOrderItemQuantity(id, quantity);
        return ResponseEntity.ok(orderItemMapper.toResponse(orderItem));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
