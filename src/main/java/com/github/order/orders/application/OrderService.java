package com.github.order.orders.application;

import com.github.order.orders.application.dto.ProductRequest;
import com.github.order.orders.application.dto.order.OrderResponse;
import com.github.order.orders.domain.Order;
import com.github.order.orders.domain.OrderProduct;
import com.github.order.orders.domain.OrderState;
import com.github.order.orders.repository.OrderRepository;
import com.github.order.products.domain.Product;
import com.github.order.products.repository.ProductRepository;
import com.github.order.user.domain.User;
import com.github.order.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.order.orders.domain.OrderState.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long create(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist."));

        Order order = Order.createOrder(findUser);
        orderRepository.save(order);

        return order.getId();
    }

    public boolean addProduct(ProductRequest requestForm) {
        Order findOrder = getOrder(requestForm.getOrderId());
        Product findProduct = getProduct(requestForm.getProductId());
        Long stock = findProduct.getQuantity();

        if (stock - requestForm.getQuantity() < 0) {
            throw new IllegalArgumentException(
                    "주문 가능 수량을 초과하였습니다. (남은 수량 : "
                    + stock + ")"
            );
        }

        OrderProduct orderProduct = OrderProduct.createOrderProduct(findOrder, findProduct, findProduct.getQuantity());
        findOrder.addOrderProduct(orderProduct);

        return true;
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long orderId) {
        OrderResponse order = orderRepository.find(orderId);

        return order;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll(Long userId, Long offset, int limit) {
        List<OrderResponse> orders = orderRepository.findAllByUserId(userId, offset, limit);

        return orders;
    }

    public Boolean submit(Long orderId, String requestMessage) {
        Order findOrder = getOrder(orderId);
        OrderState orderState = findOrder.getState();

        if (orderState.equals(PENDING)) {
            findOrder.order(requestMessage);
            return true;
        }

        return false;
    }

    public Boolean accept(Long orderId) {
        Order findOrder = getOrder(orderId);
        OrderState orderState = findOrder.getState();

        if (orderState.equals(REQUESTED)) {
            findOrder.accept();
            return true;
        }

        return false;
    }

    public Boolean cancel(Long orderId, String rejectMessage) {
        Order findOrder = getOrder(orderId);
        OrderState orderState = findOrder.getState();

        if (orderState == REQUESTED) {
            findOrder.cancel(rejectMessage);
            findOrder.getOrderProduct()
                    .forEach(orderProduct -> orderProduct.restoreStock());

            return true;
        }

        return false;
    }

    //판매자만 배송처리 가능하도록 설정해야함.
    public Boolean shipping(Long orderId) {
        Order findOrder = getOrder(orderId);
        OrderState orderState = findOrder.getState();

        if (orderState.equals(ACCEPTED)) {
            findOrder.shipping();
            return true;
        }
        return false;
    }

    public Boolean complete(Long orderId) {
        Order findOrder = getOrder(orderId);
        OrderState orderState = findOrder.getState();

        if (orderState.equals(SHIPPING)) {
            findOrder.complete();
            return true;
        }

        return false;
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order is not exist"));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product is not exist"));
    }


}
