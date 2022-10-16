package com.github.order.orders.repository;

import com.github.order.orders.application.dto.OrderResponse;
import com.github.order.orders.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

}
