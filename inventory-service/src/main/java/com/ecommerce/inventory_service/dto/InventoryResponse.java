package com.ecommerce.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {

    private Long productId;
    private String productName;
    private List<InventoryBatchResponse> batches;
}
