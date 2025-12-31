package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.dto.InventoryBatchResponse;
import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.dto.InventoryUpdateRequest;
import com.ecommerce.inventory_service.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

class InventoryControllerTest {

    private InventoryService inventoryService;
    private InventoryController inventoryController;

    @Mock
    private InventoryService service;

    @BeforeEach
    void setUp() {
        inventoryService = Mockito.mock(InventoryService.class);
        inventoryController = new InventoryController(inventoryService);
    }

    @Test
    void testGetInventory() {
        List<InventoryBatchResponse> batchResponseList = new ArrayList<>();
        batchResponseList.add(new InventoryBatchResponse(10L,50, LocalDate.now()));
        InventoryResponse mockResponse = new InventoryResponse(1L, "prod1", batchResponseList);
        Mockito.when(inventoryService.getInventory(anyLong())).thenReturn(mockResponse);

        InventoryResponse result = inventoryController.getInventory(1L);

        assertEquals(1L, result.getProductId());
        assertEquals(50, result.getBatches().get(0).getQuantity());

    }

    @Test
    void testUpdateInventory() {
        Mockito.when(inventoryService.updateInventory(anyLong(), anyInt()))
                .thenReturn(List.of(101L, 102L));

        InventoryUpdateRequest request = new InventoryUpdateRequest(1L, 10);
        List<Long> result = inventoryController.updateInventory(request);

        assertEquals(2, result.size());
        assertEquals(101L, result.get(0));
        assertEquals(102L, result.get(1));
    }
}
