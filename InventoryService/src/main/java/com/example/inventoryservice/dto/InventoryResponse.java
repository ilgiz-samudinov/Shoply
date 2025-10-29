package com.example.inventoryservice.dto;

import lombok.Data;

@Data
public class InventoryResponse {
    private Long id;
    private Long productId;

    private Long warehouseId;

    private Integer totalQuantity;

    private Integer reservedQuantity;
}
