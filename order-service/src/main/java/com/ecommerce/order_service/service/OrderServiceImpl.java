package com.ecommerce.order_service.service;

import com.ecommerce.order_service.config.InventoryClient;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.dto.OrderResponse;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderResponse placeOrder(OrderRequest request)
    {
        List<Long> reservedBatchIds = inventoryClient.reserveInventory(request.getProductId(),request.getQuantity());

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setProductName(resolveProductName(request.getProductId()));
        order.setQuantity(request.getQuantity());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        Order savedOrder = orderRepository.save(order);

        return new OrderResponse(
                savedOrder.getOrderId(),
                savedOrder.getProductId(),
                savedOrder.getProductName(),
                savedOrder.getQuantity(),
                savedOrder.getStatus(),
                reservedBatchIds,
                "Order placed. Inventory reserved."
        );
    }

    private String resolveProductName(Long productId) {
        return switch (productId.intValue()) {
            case 1001 -> "Laptop";
            case 1002 -> "Smartphone";
            case 1003 -> "Tablet";
            case 1004 -> "Headphones";
            case 1005 -> "Smartwatch";
            default -> "Unknown";
        };
    }




}
