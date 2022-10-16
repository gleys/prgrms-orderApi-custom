package com.github.order.orders.application.dto;

import com.github.order.orders.domain.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderResponse {

    private Long seq;
    private OrderState state;

    private String requestMessage;
    private String rejectMessage;
    private LocalDateTime completedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime createAt;

    private List<ProductForm> items;

    private Long totalPrice;

}
