package com.example.inventoryservice.mapper;

import com.example.inventoryservice.dto.InventoryReservationRequest;
import com.example.inventoryservice.dto.InventoryReservationResponse;
import com.example.inventoryservice.model.InventoryReservation;
import org.mapstruct.*;

@Mapper (componentModel = "spring")
public interface InventoryReservationMapper {


    @Mapping(target = "inventory", ignore = true)
    InventoryReservation toEntity(InventoryReservationRequest inventoryReservationRequest);


    @Mapping(target = "inventoryId", source = "inventory.id")
    InventoryReservationResponse toResponse(InventoryReservation inventoryReservation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void merge(@MappingTarget InventoryReservation existing, InventoryReservation inventoryReservation);
}

