package com.github.order.review.application.dto;

import com.github.order.orders.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewForm {
    private Long userId;
    private Long orderProductId;
    private String content;
}
