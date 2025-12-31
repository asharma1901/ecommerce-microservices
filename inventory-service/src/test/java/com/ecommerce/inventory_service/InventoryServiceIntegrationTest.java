package com.ecommerce.inventory_service;

import com.ecommerce.inventory_service.entity.InventoryBatch;
import com.ecommerce.inventory_service.repository.InventoryBatchRepository;
import com.ecommerce.inventory_service.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class InventoryServiceIntegrationTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryBatchRepository repository;

    @Test
    void getInventory_shouldFetchFromDatabase() {
        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(1L);
        batch.setProductId(1L);
        batch.setProductName("MacBook");
        batch.setQuantity(20);
        batch.setExpiryDate(LocalDate.now().plusDays(30));

        repository.save(batch);

        var response = inventoryService.getInventory(1L);

        assertThat(response.getProductName()).isEqualTo("MacBook");
        assertThat(response.getBatches()).hasSize(1);
    }

    @Test
    void updateInventory_shouldPersistChanges() {
        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(1L);
        batch.setProductId(1L);
        batch.setProductName("MacBook");
        batch.setQuantity(10);
        batch.setExpiryDate(LocalDate.now());

        repository.save(batch);

        List<Long> reserved = inventoryService.updateInventory(1L, 5);

        InventoryBatch updated = repository.findById(1L).orElseThrow();
        assertThat(updated.getQuantity()).isEqualTo(5);
        assertThat(reserved).contains(1L);
    }
}
