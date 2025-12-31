package com.ecommerce.inventory_service.service.factory;

import com.ecommerce.inventory_service.entity.InventoryBatch;
import com.ecommerce.inventory_service.repository.InventoryBatchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("EXPIRY")
@RequiredArgsConstructor
public class ExpiryBasedInventoryHandler implements InventoryHandler{

    private final InventoryBatchRepository repository;

    @Override
    public List<InventoryBatch> getAvailableBatches(Long productId) {
        return repository.findByProductIdOrderByExpiryDateAsc(productId);
    }

    @Override
    @Transactional
    public List<Long> deductInventory(Long productId, int quantity) {
        List<InventoryBatch> batches =
                repository.findByProductIdOrderByExpiryDateAsc(productId);

        List<Long> reservedBatchIds = new ArrayList<>();

        for (InventoryBatch batch : batches) {
            if (quantity <= 0) break;

            int deduct = Math.min(batch.getQuantity(), quantity);
            if(deduct>0)
            {
                batch.setQuantity(batch.getQuantity() - deduct);
                quantity -= deduct;
                reservedBatchIds.add(Long.valueOf(batch.getBatchId()));
                repository.save(batch);
            }
        }

        if (quantity > 0) {
            throw new RuntimeException("Insufficient inventory");
        }

        return reservedBatchIds;
    }
}
