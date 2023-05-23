package com.example.orderservice.request;

import com.example.orderservice.response.OrderLineItemsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private List<OrderLineItemsResponse> orderLineItemsResponseList;
}
