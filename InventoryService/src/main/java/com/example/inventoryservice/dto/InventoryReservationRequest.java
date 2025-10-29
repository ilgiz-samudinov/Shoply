package com.example.inventoryservice.dto;

import com.example.inventoryservice.model.ReservationStatus;
import lombok.Data;

@Data
public class InventoryReservationRequest {

    private Long inventoryId;

    private Long orderId;


    private Integer quantity;

//    private ReservationStatus reservationStatus ;



}
