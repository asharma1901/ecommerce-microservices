package com.ecommerce.inventory_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inventory_batch")
@Data
public class InventoryBatch {

    @Id
    private Long batchId;
    private Long productId;
    private String productName;
    private int quantity;
    private LocalDate expiryDate;
}
