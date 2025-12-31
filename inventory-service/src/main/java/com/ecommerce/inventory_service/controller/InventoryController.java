package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.dto.InventoryUpdateRequest;
import com.ecommerce.inventory_service.entity.InventoryBatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.inventory_service.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public InventoryResponse getInventory(@PathVariable Long productId) {
        return inventoryService.getInventory(productId);
    }

    @PostMapping("/update")
    public List<Long> updateInventory(@RequestBody InventoryUpdateRequest request) {
        return inventoryService.updateInventory(
                request.getProductId(), request.getQuantity());
    }
}
