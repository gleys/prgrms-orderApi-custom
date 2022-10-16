package com.github.order.orders.domain;

import com.github.order.common.BaseEntity;
import com.github.order.products.domain.Product;
import com.github.order.review.domain.Review;
import com.github.order.user.domain.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.github.order.orders.domain.OrderState.*;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "orders")
@Data
@NoArgsConstructor(access = PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(STRING)
    private OrderState state;

    @Column(length = 1000, nullable = true)
    private String requestMessage;

    @Column(length = 1000, nullable = true)
    private String rejectMessage;

    @Column(nullable = true)
    private LocalDateTime completedAt;

    @Column(nullable = true)
    private LocalDateTime rejectedAt;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_orders_to_users"))
    private User user;

    @OneToMany(cascade = ALL, mappedBy = "order")
    private List<OrderProduct> orderProduct = new ArrayList<>();

    public Order(OrderState state, User user) {
        this.state = state;
        this.user = user;
    }

    public static Order createOrder(User user) {
        Order order = new Order();
        order.user = user;
        order.state = PENDING;

        return order;
    }

    public Long getTotalPrice() {
        return orderProduct.stream()
                .mapToLong(orderProduct -> orderProduct.totalPrice()).sum();
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProduct.add(orderProduct);
    }

    public void order(String requestMessage) {
        this.state = REQUESTED;
        this.requestMessage = requestMessage;
    }

    public void accept() {
        this.state = ACCEPTED;
    }

    public void cancel(String rejectMessage) {
        this.state = REJECTED;
        this.rejectMessage = rejectMessage;
        this.rejectedAt = LocalDateTime.now();
    }

    public void shipping() {
        this.state = SHIPPING;
    }

    public void complete() {
        this.state = COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

}
