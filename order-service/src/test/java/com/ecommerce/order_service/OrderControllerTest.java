package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

class OrderControllerTest {

    private OrderService orderService;
    private OrderController orderController;

    @Mock
    private OrderService service;

    @BeforeEach
    void setUp() {
        orderService = Mockito.mock(OrderService.class);
        orderController = new OrderController(orderService);
    }

    @Test
    void testPlaceOrder() {

        OrderRequest request = new OrderRequest(1002L, 3);

        OrderResponse mockResponse = new OrderResponse(
                5012L,
                1002L,
                "Smartphone",
                3,
                "PLACED",
                List.of(11L, 12L),
                "Order placed. Inventory reserved."
        );

        Mockito.when(orderService.placeOrder(any(OrderRequest.class)))
                .thenReturn(mockResponse);

        OrderResponse result = orderController.placeOrder(request);

        assertEquals(5012L, result.getOrderId());
        assertEquals(1002L, result.getProductId());
        assertEquals("Smartphone", result.getProductName());
        assertEquals(3, result.getQuantity());
        assertEquals("PLACED", result.getStatus());
        assertEquals(2, result.getReservedFromBatchIds().size());
        assertEquals(11L, result.getReservedFromBatchIds().get(0));
    }
}
