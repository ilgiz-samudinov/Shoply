package com.example.inventoryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "inventories",
        indexes = {
                @Index(name = "idx_inventory_product", columnList = "product_id"),
                @Index(name = "idx_inventory_warehouse", columnList = "warehouse_id"),
                @Index(name = "idx_inventory_product_warehouse", columnList = "product_id, warehouse_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_product_warehouse", columnNames = {"product_id", "warehouse_id"})
        })
public class Inventory {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name =  "product_id", nullable = false)
    private Long productId;


    @Column(name = "warehouse_id", nullable = false)
    private Long warehouseId;


    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity = 0;


    @Column(nullable = false, name = "reserved_quantity")
    private Integer reservedQuantity = 0;

    @Version
    private int version;



    public Integer getAvailableQuantity() {
        int total = (totalQuantity == null) ? 0 : totalQuantity;
        int reserved = (reservedQuantity == null) ? 0 : reservedQuantity;
        int available = total - reserved;
        return Math.max(0, available);
    }

    public void reserve(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        int available = getAvailableQuantity();
        if (available < quantity) {
            throw new IllegalStateException("Not enough available quantity to reserve");
        }
        this.reservedQuantity = (this.reservedQuantity == null ? 0 : this.reservedQuantity) + quantity;
    }


    public void release(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        int reserved = (this.reservedQuantity == null) ? 0 : this.reservedQuantity;
        if (reserved < quantity) {
            throw new IllegalStateException("Not enough reserved quantity to release");
        }
        this.reservedQuantity = reserved - quantity;
    }


    public void consume(Integer quantity){
        if(quantity == null || quantity <= 0){
            throw new IllegalArgumentException("Quantity must be positive");
        }


        int reserved = (this.reservedQuantity == null) ? 0 : this.reservedQuantity;
        int total = (this.totalQuantity == null) ? 0 : this.totalQuantity;


        if (reserved < quantity) {
            throw new IllegalStateException("Not enough reserved quantity to consume");
        }
        if (total < quantity) {
            throw new IllegalStateException("Not enough total quantity to consume");
        }


        this.reservedQuantity = reserved - quantity;
        this.totalQuantity = total - quantity;
    }

}
