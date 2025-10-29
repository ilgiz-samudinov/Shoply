package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.WarehouseRequest;
import com.example.inventoryservice.dto.WarehouseResponse;
import com.example.inventoryservice.mapper.WarehouseMapper;
import com.example.inventoryservice.model.Warehouse;
import com.example.inventoryservice.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    @PostMapping
    public ResponseEntity<WarehouseResponse> createWarehouse(@Valid @RequestBody WarehouseRequest warehouseRequest) {
        Warehouse warehouse = warehouseService.createWarehouse(warehouseRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(warehouseMapper.toResponse(warehouse));
    }

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAllWarehouses() {
        List<WarehouseResponse> responses = warehouseService.getAllWarehouses()
                .stream()
                .map(warehouseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WarehouseResponse> getWarehouseById(@PathVariable Long id) {
        return warehouseService.findById(id)
                .map(warehouse -> ResponseEntity.ok(warehouseMapper.toResponse(warehouse)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WarehouseResponse> updateWarehouse(@PathVariable Long id,
                                                             @Valid @RequestBody WarehouseRequest warehouseRequest) {
        return warehouseService.updateWarehouse(id, warehouseRequest)
                .map(warehouse -> ResponseEntity.ok(warehouseMapper.toResponse(warehouse)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        if (warehouseService.deleteWarehouse(id)) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
