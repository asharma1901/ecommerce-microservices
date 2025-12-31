package com.ecommerce.order_service.exception;

public class InventoryUnavailableException extends RuntimeException{
    public InventoryUnavailableException(String message) {
        super(message);
    }
}
