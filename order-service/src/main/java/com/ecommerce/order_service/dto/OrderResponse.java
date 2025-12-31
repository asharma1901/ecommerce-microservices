package com.ecommerce.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long productId;
    private String productName;
    private int quantity;
    private String status;
    private List<Long> reservedFromBatchIds;
    private String message;
}
