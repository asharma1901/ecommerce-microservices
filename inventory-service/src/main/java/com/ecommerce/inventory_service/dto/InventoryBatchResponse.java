package com.ecommerce.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBatchResponse {

    private Long batchId;
    private int quantity;
    private LocalDate expiryDate;
}
