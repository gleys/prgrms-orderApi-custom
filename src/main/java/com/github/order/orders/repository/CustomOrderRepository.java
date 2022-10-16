package com.github.order.orders.repository;

import com.github.order.orders.application.dto.order.OrderResponse;

import java.util.List;

public interface CustomOrderRepository {
    List<OrderResponse> findAllByUserId(Long userId, Long offset, int limit);
    OrderResponse find(Long orderId);

}
