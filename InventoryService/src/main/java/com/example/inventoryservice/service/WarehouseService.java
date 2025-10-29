package com.example.inventoryservice.service;

import com.example.inventoryservice.dto.WarehouseRequest;
import com.example.inventoryservice.mapper.WarehouseMapper;
import com.example.inventoryservice.model.Warehouse;
import com.example.inventoryservice.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Transactional
    public Warehouse createWarehouse(WarehouseRequest warehouseRequest) {
        Warehouse warehouse = warehouseMapper.toEntity(warehouseRequest);
        return warehouseRepository.save(warehouse);
    }

    @Transactional(readOnly = true)
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Transactional
    public Optional<Warehouse> updateWarehouse(Long id, WarehouseRequest warehouseRequest) {
        return warehouseRepository.findById(id)
                .map(existingWarehouse -> {
                    Warehouse newData = warehouseMapper.toEntity(warehouseRequest);
                    warehouseMapper.merge(existingWarehouse, newData);
                    return warehouseRepository.save(existingWarehouse);
                });
    }

    @Transactional
    public boolean deleteWarehouse(Long id) {
        if (warehouseRepository.existsById(id)) {
            warehouseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
