package com.ecommerce.order_service.config;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class InventoryClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Long> reserveInventory(Long productId, int quantity)
    {
        String url = "http://localhost:8081/inventory/update";
        var request = new java.util.HashMap<String, Object>();
        request.put("productId", productId);
        request.put("quantity", quantity);
        List<Integer> raw = restTemplate.postForObject(url, request, List.class);

        return raw.stream()
                .map(Long::valueOf)
                .toList();
    }

}
