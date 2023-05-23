package com.example.orderservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLineItemsResponse {

    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
