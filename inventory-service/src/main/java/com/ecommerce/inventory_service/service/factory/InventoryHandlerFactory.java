package com.ecommerce.inventory_service.service.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class InventoryHandlerFactory {

    private final Map<String, InventoryHandler> handlers;

    public InventoryHandler getHandler(String type) {
        return handlers.get(type);
    }
}
