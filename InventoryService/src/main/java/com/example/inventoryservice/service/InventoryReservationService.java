package com.example.inventoryservice.service;

import com.example.inventoryservice.client.OrderServiceClient;
import com.example.inventoryservice.dto.InventoryReservationRequest;
import com.example.inventoryservice.dto.external.Order;
import com.example.inventoryservice.exception.InsufficientResourcesException;
import com.example.inventoryservice.exception.InventoryReservationAlreadyCompletedException;
import com.example.inventoryservice.exception.NotFoundException;
import com.example.inventoryservice.mapper.InventoryReservationMapper;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.model.InventoryReservation;
import com.example.inventoryservice.model.ReservationStatus;
import com.example.inventoryservice.repository.InventoryRepository;
import com.example.inventoryservice.repository.InventoryReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryReservationService {
    private final InventoryReservationRepository inventoryReservationRepository;
    private final InventoryReservationMapper inventoryReservationMapper;
    private final OrderServiceClient orderServiceClient;
    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;
    private final Clock clock;

    
    @Transactional
    public InventoryReservation createInventoryReservation(InventoryReservationRequest inventoryReservationRequest) {
        Instant now = Instant.now(clock);

        Inventory inventory = inventoryService.getInventoryById(inventoryReservationRequest.getInventoryId());
        Order order = getOrderById(inventoryReservationRequest.getOrderId());
        reserveInventory(inventory.getId(), inventoryReservationRequest.getQuantity());
        InventoryReservation inventoryReservation = inventoryReservationMapper.toEntity(inventoryReservationRequest);
        inventoryReservation.setReservedAt(now);
        inventoryReservation.setInventory(inventory);
        return inventoryReservationRepository.save(inventoryReservation);
    }


    @Transactional(readOnly = true)
    public List<InventoryReservation> getAllInventoryReservations(){
        return inventoryReservationRepository.findAll();
    }


    @Transactional(readOnly = true)
    public InventoryReservation getInventoryReservationById(Long id) {
        return inventoryReservationRepository.findById(id)
                .orElseThrow (()-> new NotFoundException("InventoryReservation not found"));
    }


    @Transactional
    public InventoryReservation updateInventoryReservation(Long id,
                                                           InventoryReservationRequest inventoryReservationRequest){

        InventoryReservation existing = getInventoryReservationById(id);
        Inventory inventory = inventoryService.getInventoryById(inventoryReservationRequest.getInventoryId());
        Order order = getOrderById(inventoryReservationRequest.getOrderId());
        InventoryReservation inventoryReservation = inventoryReservationMapper.toEntity(inventoryReservationRequest);
        inventoryReservationMapper.merge(existing, inventoryReservation);
        inventoryReservation.setInventory(inventory);
        return inventoryReservationRepository.save(existing);
    }


    @Transactional
    public void deleteInventoryReservationById(Long id){
        if(!inventoryReservationRepository.existsById(id)){
            throw new NotFoundException("Inventory reservation not found");
        }
        inventoryReservationRepository.deleteById(id);
    }



    @Transactional
    public void releaseInventoryReservation(Long inventoryReservationId){
        InventoryReservation inventoryReservation = getInventoryReservationById(inventoryReservationId);

        if(inventoryReservation.getReservationStatus() == ReservationStatus.CONSUMED){
            throw new InventoryReservationAlreadyCompletedException(
                    "Cannot release reservation " + inventoryReservation.getId() + " because it is already completed");
        }
        releaseInventory(inventoryReservation.getInventory().getId(), inventoryReservation.getQuantity());
        inventoryReservation.release();
        inventoryReservationRepository.save(inventoryReservation);
    }


    @Transactional
    public void expireInventoryReservation (Long inventoryReservationId){
        Instant now = Instant.now(clock);
        InventoryReservation inventoryReservation = getInventoryReservationById(inventoryReservationId);
        if(inventoryReservation.isExpired(now)){
            inventoryReservation.expire(now);
            releaseInventory(inventoryReservation.getInventory().getId(), inventoryReservation.getQuantity());
            inventoryReservationRepository.save(inventoryReservation);
        }
    }




    @Transactional
    public void consumeInventoryReservation(Long inventoryReservationId){
        InventoryReservation  inventoryReservation = getInventoryReservationById(inventoryReservationId);
        consumeInventory(inventoryReservation.getInventory().getId(), inventoryReservation.getQuantity());
        inventoryReservation.consume();
        inventoryReservationRepository.save(inventoryReservation);
    }




    private Order getOrderById(Long id) {
        Order order = orderServiceClient.getOrderById(id);
        if(order == null){
            throw new NotFoundException("Order not found");
        }
        return order;
    }






    @Transactional
    public void releaseInventory(Long inventoryId, Integer quantity){
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Inventory inventory = inventoryService.getInventoryById(inventoryId);
        inventory.release(quantity);
        inventoryRepository.save(inventory);
    }







    @Transactional
    public void consumeInventory(Long inventoryId, Integer quantity){
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        Inventory inventory = inventoryService.getInventoryById(inventoryId);
        inventory.consume(quantity);
        inventoryRepository.save(inventory);
    }


    @Transactional
    public void reserveInventory(Long inventoryId, Integer quantity){
        Inventory inventory = inventoryService.getInventoryById(inventoryId);
        Integer available = inventory.getAvailableQuantity();
        if(available < quantity){
            throw new InsufficientResourcesException("Quantity exceeds total quantity");
        }
        inventory.reserve(quantity);
        inventoryRepository.save(inventory);
    }





}

