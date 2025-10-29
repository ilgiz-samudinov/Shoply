package com.example.inventoryservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "inventory_reservations",
        indexes = {
                @Index(name = "idx_res_order", columnList = "order_id"),
                @Index(name = "idx_res_inventory", columnList = "inventory_id"),
                @Index(name = "idx_res_status", columnList = "reservation_status"),
                @Index(name = "idx_res_expired", columnList = "expired_at")
        })
public class InventoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "inventory_id", nullable  = false)
    private Inventory inventory;

    @Column(nullable = false, name = "order_id")
    private Long orderId;


    @Column(nullable = false, name = "quantity")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "reservation_status")
    private ReservationStatus reservationStatus = ReservationStatus.RESERVED;

    @Column(name = "reserved_at", nullable = false)
    private Instant reservedAt;


    @Column(name = "expired_at")
    private Instant expiredAt;

    public boolean isExpired(Instant now) {
        return expiredAt != null && now.isAfter(expiredAt);
    }


    public boolean isActive(Instant now) {
        return !isExpired(now) && reservationStatus == ReservationStatus.RESERVED;
    }


    public void consume() {
        if (reservationStatus != ReservationStatus.RESERVED) {
            throw new IllegalStateException("Only RESERVED can be consumed");
        }
        reservationStatus = ReservationStatus.CONSUMED;
    }


    public void release() {
        if (reservationStatus != ReservationStatus.RESERVED) {
            throw new IllegalStateException("Only RESERVED can be released");
        }
        reservationStatus = ReservationStatus.CANCELLED;
    }


    public void expire(Instant now) {
        if (!isExpired(now)) throw new IllegalStateException("Reservation is not expired");
        reservationStatus = ReservationStatus.EXPIRED;
    }

}
