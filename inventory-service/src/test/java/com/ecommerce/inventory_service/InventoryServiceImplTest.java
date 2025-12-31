package com.ecommerce.inventory_service;

import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.entity.InventoryBatch;
import com.ecommerce.inventory_service.service.InventoryServiceImpl;
import com.ecommerce.inventory_service.service.factory.InventoryHandler;
import com.ecommerce.inventory_service.service.factory.InventoryHandlerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {

    @Mock
    private InventoryHandlerFactory factory;

    @Mock
    private InventoryHandler handler;

    @InjectMocks
    private InventoryServiceImpl service;

    @Test
    void getInventory_shouldReturnInventoryResponse() {
        Long productId = 1L;

        InventoryBatch batch = new InventoryBatch();
        batch.setBatchId(101L);
        batch.setProductId(productId);
        batch.setProductName("iPhone");
        batch.setQuantity(10);
        batch.setExpiryDate(LocalDate.now().plusDays(10));

        when(factory.getHandler("EXPIRY")).thenReturn(handler);
        when(handler.getAvailableBatches(productId)).thenReturn(List.of(batch));

        InventoryResponse response = service.getInventory(productId);

        assertThat(response.getProductId()).isEqualTo(productId);
        assertThat(response.getProductName()).isEqualTo("iPhone");
        assertThat(response.getBatches()).hasSize(1);
    }

    @Test
    void updateInventory_shouldDelegateToHandler() {
        when(factory.getHandler("EXPIRY")).thenReturn(handler);
        when(handler.deductInventory(1L, 5)).thenReturn(List.of(101L));

        List<Long> result = service.updateInventory(1L, 5);

        assertThat(result).containsExactly(101L);
        verify(handler).deductInventory(1L, 5);
    }

}
