package com.github.order.orders.domain;

public enum OrderState {
    PENDING,
    REQUESTED,
    ACCEPTED,
    SHIPPING,
    COMPLETED,
    REJECTED
}