package com.ecommerce.inventory_service.repository;

import com.ecommerce.inventory_service.entity.InventoryBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryBatchRepository extends JpaRepository<InventoryBatch, Long> {

    List<InventoryBatch> findByProductIdOrderByExpiryDateAsc(Long productId);

}
