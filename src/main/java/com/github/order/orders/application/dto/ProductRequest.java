package com.github.order.orders.application.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private final Long orderId;
    private final Long productId;
    private final Long quantity;
}
