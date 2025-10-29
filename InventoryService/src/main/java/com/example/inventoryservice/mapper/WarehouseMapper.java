package com.example.inventoryservice.mapper;

import com.example.inventoryservice.dto.WarehouseRequest;
import com.example.inventoryservice.dto.WarehouseResponse;
import com.example.inventoryservice.model.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    Warehouse toEntity(WarehouseRequest warehouseRequest);
    WarehouseResponse toResponse(Warehouse warehouse);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void merge(@MappingTarget Warehouse existing, Warehouse warehouse);
}
