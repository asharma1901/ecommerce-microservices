package com.ecommerce.order_service;

import com.ecommerce.order_service.config.InventoryClient;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private InventoryClient inventoryClient;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void placeOrder_shouldCreateOrderAndReserveInventory() {

        // given
        OrderRequest request = new OrderRequest();
        request.setProductId(1001L);
        request.setQuantity(2);

        List<Long> reservedBatchIds = List.of(11L, 12L);

        Order savedOrder = new Order();
        savedOrder.setOrderId(1L);
        savedOrder.setProductId(1001L);
        savedOrder.setProductName("Laptop");
        savedOrder.setQuantity(2);
        savedOrder.setStatus("PLACED");
        savedOrder.setOrderDate(LocalDate.now());

        when(inventoryClient.reserveInventory(1001L, 2))
                .thenReturn(reservedBatchIds);

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        // when
        OrderResponse response = orderService.placeOrder(request);

        // then
        assertThat(response.getOrderId()).isEqualTo(1L);
        assertThat(response.getProductId()).isEqualTo(1001L);
        assertThat(response.getProductName()).isEqualTo("Laptop");
        assertThat(response.getQuantity()).isEqualTo(2);
        assertThat(response.getStatus()).isEqualTo("PLACED");
        assertThat(response.getReservedFromBatchIds()).containsExactly(11L, 12L);
        assertThat(response.getMessage()).isEqualTo("Order placed. Inventory reserved.");

        verify(inventoryClient).reserveInventory(1001L, 2);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void placeOrder_shouldStillSaveOrderEvenIfNoBatchIdsReturned() {

        // given
        OrderRequest request = new OrderRequest();
        request.setProductId(1005L);
        request.setQuantity(1);

        when(inventoryClient.reserveInventory(1005L, 1))
                .thenReturn(List.of());

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    order.setOrderId(2L);
                    return order;
                });

        // when
        OrderResponse response = orderService.placeOrder(request);

        // then
        assertThat(response.getOrderId()).isEqualTo(2L);
        assertThat(response.getProductName()).isEqualTo("Smartwatch");
        assertThat(response.getReservedFromBatchIds()).isEmpty();

        verify(inventoryClient).reserveInventory(1005L, 1);
        verify(orderRepository).save(any(Order.class));
    }
}
