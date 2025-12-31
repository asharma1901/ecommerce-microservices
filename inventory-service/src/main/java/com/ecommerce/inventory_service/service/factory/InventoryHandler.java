package com.ecommerce.inventory_service.service.factory;

import com.ecommerce.inventory_service.entity.InventoryBatch;

import java.util.List;

public interface InventoryHandler {
    List<InventoryBatch> getAvailableBatches(Long productId);
    List<Long> deductInventory(Long productId, int quantity);
}
