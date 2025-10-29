package com.example.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WarehouseRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
}
