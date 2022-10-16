package com.github.order.orders.application.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ReviewForm {
    private Long seq;
    private Long productId;
    private String content;
    private LocalDateTime createAt;
}
