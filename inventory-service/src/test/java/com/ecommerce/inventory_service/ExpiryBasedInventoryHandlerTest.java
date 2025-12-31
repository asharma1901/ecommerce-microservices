package com.ecommerce.inventory_service;

import com.ecommerce.inventory_service.entity.InventoryBatch;
import com.ecommerce.inventory_service.repository.InventoryBatchRepository;
import com.ecommerce.inventory_service.service.factory.ExpiryBasedInventoryHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpiryBasedInventoryHandlerTest {

    @Mock
    private InventoryBatchRepository repository;

    @InjectMocks
    private ExpiryBasedInventoryHandler handler;

    @Test
    void deductInventory_shouldReduceQuantityAndReturnBatchIds() {
        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(1L);
        batch.setQuantity(10);
        batch.setExpiryDate(LocalDate.now());

        when(repository.findByProductIdOrderByExpiryDateAsc(1L))
                .thenReturn(List.of(batch));

        List<Long> result = handler.deductInventory(1L, 5);

        assertThat(batch.getQuantity()).isEqualTo(5);
        assertThat(result).containsExactly(1L);
        verify(repository).save(batch);
    }

    @Test
    void deductInventory_shouldThrowIfInsufficientStock() {
        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(1L);
        batch.setQuantity(2);

        when(repository.findByProductIdOrderByExpiryDateAsc(1L))
                .thenReturn(List.of(batch));

        assertThatThrownBy(() -> handler.deductInventory(1L, 5))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Insufficient inventory");
    }
}
