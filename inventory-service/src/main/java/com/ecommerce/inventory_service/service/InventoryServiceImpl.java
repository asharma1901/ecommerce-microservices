package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.InventoryBatchResponse;
import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.entity.InventoryBatch;
import com.ecommerce.inventory_service.service.factory.InventoryHandlerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService{

    private final InventoryHandlerFactory factory;

    @Override
    public InventoryResponse getInventory(Long productId) {
        List<InventoryBatch> batches = factory.getHandler("EXPIRY").getAvailableBatches(productId);

        if(batches.isEmpty())
        {
            return new InventoryResponse(productId, null, List.of());
        }
        String productName = batches.get(0).getProductName();

        return new InventoryResponse(
                productId,
                productName,
                batches.stream()
                        .map(b -> new InventoryBatchResponse(
                                b.getBatchId(),
                                b.getQuantity(),
                                b.getExpiryDate()))
                        .toList()
        );
    }

    @Override
    public List<Long> updateInventory(Long productId, int quantity) {
        return factory.getHandler("EXPIRY").deductInventory(productId, quantity);
    }
}
