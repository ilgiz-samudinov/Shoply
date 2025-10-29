package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.InventoryReservationRequest;
import com.example.inventoryservice.dto.InventoryReservationResponse;
import com.example.inventoryservice.mapper.InventoryReservationMapper;
import com.example.inventoryservice.model.InventoryReservation;
import com.example.inventoryservice.service.InventoryReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory-reservations")
public class InventoryReservationController {

    private final InventoryReservationService reservationService;
    private final InventoryReservationMapper reservationMapper;

    @PostMapping
    public ResponseEntity<InventoryReservationResponse> createReservation(
            @RequestBody @Valid InventoryReservationRequest request) {

        InventoryReservation saved = reservationService.createInventoryReservation(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(reservationMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<InventoryReservationResponse>> getAllReservations() {
        List<InventoryReservationResponse> list = reservationService.getAllInventoryReservations()
                .stream().map(reservationMapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryReservationResponse> getReservationById(@PathVariable Long id) {
        InventoryReservation reservation = reservationService.getInventoryReservationById(id);
        return ResponseEntity.ok(reservationMapper.toResponse(reservation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryReservationResponse> updateReservation(
            @PathVariable Long id,
            @RequestBody @Valid InventoryReservationRequest request) {

        InventoryReservation updated = reservationService.updateInventoryReservation(id, request);
        return ResponseEntity.ok(reservationMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteInventoryReservationById(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/{id}/consume")
    public ResponseEntity<Void> consumeReservation(@PathVariable Long id) {
        reservationService.consumeInventoryReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/release")
    public ResponseEntity<Void> releaseReservation(@PathVariable Long id) {
        reservationService.releaseInventoryReservation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/expire")
    public ResponseEntity<Void> expireReservation(@PathVariable Long id) {
        reservationService.expireInventoryReservation(id);
        return ResponseEntity.noContent().build();
    }




}
