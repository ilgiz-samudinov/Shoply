package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.dto.InventoryResponse;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryMapper inventoryMapper;

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@Valid @RequestBody InventoryRequest inventoryRequest){
        Inventory inventory = inventoryService.createInventory(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryMapper.toResponse(inventory));
    }


    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getAllInventory(){
        return ResponseEntity.status(HttpStatus.OK).body(
                inventoryService.getAllInventory().stream().map(inventoryMapper::toResponse).toList()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryById(@PathVariable Long id){
        Inventory inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(inventoryMapper.toResponse(inventory));
    }



    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponse> updateInventory(@PathVariable Long id,
                                                             @Valid @RequestBody InventoryRequest inventoryRequest){
        Inventory inventory = inventoryService.updateInventory(id, inventoryRequest);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryMapper.toResponse(inventory));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id){
        inventoryService.deleteInventoryById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
