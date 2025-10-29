package com.example.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class InventoryRequest {
    @NotNull
    private Long productId;
    @NotNull
    private Long warehouseId;
    @NotNull @Min(0)
    private Integer totalQuantity;
}
