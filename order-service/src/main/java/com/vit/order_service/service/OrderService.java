package com.vit.order_service.service;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vit.order_service.dto.OrderLineItemsDto;
import com.vit.order_service.dto.OrderRequest;
import com.vit.order_service.model.Order;
import com.vit.order_service.model.OrderLineItems;
import com.vit.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(order);
    }

    private OrderLineItems mapToEntity(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}