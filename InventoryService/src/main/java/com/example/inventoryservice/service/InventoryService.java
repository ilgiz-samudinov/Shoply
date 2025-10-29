package com.example.inventoryservice.service;

import com.example.inventoryservice.client.ProductServiceClient;
import com.example.inventoryservice.dto.InventoryRequest;
import com.example.inventoryservice.dto.external.Product;
import com.example.inventoryservice.exception.NotFoundException;
import com.example.inventoryservice.mapper.InventoryMapper;
import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ProductServiceClient productServiceClient;




    @Transactional
    public Inventory createInventory(InventoryRequest inventoryRequest){
        Product product = getProductById(inventoryRequest.getProductId());
        Inventory inventory = inventoryMapper.toEntity(inventoryRequest);
        if (inventory.getTotalQuantity() == null) inventory.setTotalQuantity(0);
        if (inventory.getReservedQuantity() == null) inventory.setReservedQuantity(0);
        return inventoryRepository.save(inventory);
    }

    @Transactional(readOnly = true)
    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Inventory getInventoryById(Long id){
        return inventoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Inventory not found"));
    }


    @Transactional
    public Inventory updateInventory(Long id, InventoryRequest inventoryRequest){
        Inventory existing = getInventoryById(id);
        Product product = getProductById(inventoryRequest.getProductId());
        Inventory inventory = inventoryMapper.toEntity(inventoryRequest);
        inventoryMapper.merge(existing, inventory);
        return inventoryRepository.save(existing);
    }

    @Transactional
    public void deleteInventoryById(Long id){
        if(!inventoryRepository.existsById(id)){
            throw new NotFoundException("Inventory not found");
        }
        inventoryRepository.deleteById(id);
    }



    public Product getProductById(Long id){
        Product product = productServiceClient.getProductById(id);
        if(product == null){
            throw new NotFoundException("Product not found");
        }
        return product;
    }

}
