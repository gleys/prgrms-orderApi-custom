package com.github.order.orders.repository;

import com.github.order.orders.application.dto.order.OrderResponse;
import com.github.order.orders.application.dto.order.ProductForm;
import com.github.order.orders.application.dto.order.ReviewForm;
import com.github.order.orders.domain.Order;
import com.github.order.orders.domain.OrderProduct;
import com.github.order.orders.domain.QOrder;
import com.github.order.orders.domain.QOrderProduct;

import com.github.order.products.domain.QProduct;
import com.github.order.review.domain.QReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements CustomOrderRepository{

    private final JPAQueryFactory query;

    @Override
    public List<OrderResponse> findAllByUserId(Long userId, Long offset, int limit) {
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QReview review = QReview.review;
        QProduct product = QProduct.product;

        List<Order> orders = query
                .selectFrom(order)
                .innerJoin(order.orderProduct, orderProduct).fetchJoin()
                .innerJoin(orderProduct.product, product).fetchJoin()
                .leftJoin(orderProduct.review, review).fetchJoin()
                .where(order.user.id.eq(userId))
                .offset(offset)
                .limit(limit)
                .orderBy(order.createAt.asc())
                .fetch();

        List<OrderResponse> result = orders.stream()
                .map(elem -> makeOrderResponse(elem))
                .collect(Collectors.toList());

        return result;
    }


    @Override
    public OrderResponse find(Long orderId) {
        QOrder order = QOrder.order;
        QReview review = QReview.review;
        QProduct product = QProduct.product;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        Order result = query
                .selectFrom(order)
                .innerJoin(order.orderProduct, orderProduct).fetchJoin()
                .leftJoin(orderProduct.review, review).fetchJoin()
                .innerJoin(orderProduct.product, product).fetchJoin()
                .where(order.id.eq(orderId))
                .fetchOne();

        OrderResponse orderResponse = makeOrderResponse(result);

        return orderResponse;
    }

    private OrderResponse makeOrderResponse(Order elem) {

        return OrderResponse.builder()
                .seq(elem.getId())
                .state(elem.getState())
                .requestMessage(elem.getRequestMessage())
                .rejectMessage(elem.getRejectMessage())
                .completedAt(elem.getCompletedAt())
                .rejectedAt(elem.getRejectedAt())
                .createAt(elem.getCreateAt())
                .items(getProductForms(elem.getOrderProduct()))
                .totalPrice(elem.getTotalPrice())
                .build();
    }

    private static List<ProductForm> getProductForms(List<OrderProduct> orderProducts) {
        log.info("orderProducts length {}", orderProducts.size());

        List<ProductForm> productForms = orderProducts
                .stream().map(
                        product ->
                            new ProductForm(
                                    product.getId(),
                                    //리뷰가 없을 때 고려해야함
                                    product.getReview() == null ?
                                    null :
                                    new ReviewForm(
                                            product.getReview().getId(),
                                            product.getId(),
                                            product.getReview().getContent(),
                                            product.getReview().getCreateAt()
                                    ),
                                    product.getProduct().getName(),
                                    product.getProduct().getPrice(),
                                    product.getQuantity(),
                                    product.totalPrice()
                            )
                ).collect(Collectors.toList());

        return productForms;
    }

}
