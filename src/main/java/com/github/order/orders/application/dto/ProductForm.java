package com.github.order.orders.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductForm {
    private long productId;
    private ReviewForm review;

    private String name;
    private Long productPrice;
    private Long quantity;
    private Long totalPrice;
}
