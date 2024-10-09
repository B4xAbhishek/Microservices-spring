package com.vit.order_service.service;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vit.order_service.dto.OrderLineItemsDto;
import com.vit.order_service.dto.OrderRequest;
import com.vit.order_service.model.Order;
import com.vit.order_service.model.OrderLineItems;
import com.vit.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        if (orderRequest.getOrderLineItemsList() == null || orderRequest.getOrderLineItemsList().isEmpty()) {
            throw new IllegalArgumentException("Order line items list cannot be null or empty");
        }

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = orderLineItemsList.stream()
                .map(OrderLineItems::getSkuCode)
                .collect(Collectors.toList());

        // Example of WebClient usage, adjust as per your actual endpoint and response
        // handling
        Boolean result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("http://localhost:8082/api/inventory/")
                        .queryParam("skuCodes", skuCodes)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (result == null || !result) {
            throw new IllegalArgumentException("Product is not available in inventory");
        } else {
            orderRepository.save(order);
        }
    }

    private OrderLineItems mapToEntity(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
