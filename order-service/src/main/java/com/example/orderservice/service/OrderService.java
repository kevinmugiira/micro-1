package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineItems;
import com.example.orderservice.repository.OrderRepo;
import com.example.orderservice.request.OrderRequest;
import com.example.orderservice.response.OrderLineItemsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepo orderRepo;

    public void placeOrder(OrderRequest orderRequest) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsResponseList()
                .stream()
                .map(this::mapToResponse)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        // Call inventory service, and place order if product is in stock
        orderRepo.save(order);
    }

    private OrderLineItems mapToResponse(OrderLineItemsResponse orderLineItemsResponse) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsResponse.getSkuCode());
        orderLineItems.setPrice(orderLineItemsResponse.getPrice());
        orderLineItems.setQuantity(orderLineItems.getQuantity());

        return orderLineItems;

    }

}
