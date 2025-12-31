package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    InventoryResponse getInventory(Long productId);
    List<Long> updateInventory(Long productId, int quantity);
}
