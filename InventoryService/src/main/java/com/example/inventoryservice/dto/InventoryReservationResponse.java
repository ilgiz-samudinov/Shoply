    package com.example.inventoryservice.dto;

    import com.example.inventoryservice.model.ReservationStatus;
    import lombok.Data;

    import java.time.Instant;
    @Data
    public class InventoryReservationResponse {

        private Long id;

        private Long inventoryId;

        private Long orderId;


        private Integer quantity;

        private ReservationStatus reservationStatus = ReservationStatus.RESERVED;


        private Instant reservedAt;

        private Instant expiredAt;
    }
