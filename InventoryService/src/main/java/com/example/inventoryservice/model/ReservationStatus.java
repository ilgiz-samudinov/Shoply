package com.example.inventoryservice.model;

public enum ReservationStatus {
    RESERVED,   // зарезервировано
    CANCELLED,   // освобождено (по отмене)
    CONSUMED,   // списано при завершении заказа
    EXPIRED     // автоматически истёк
}