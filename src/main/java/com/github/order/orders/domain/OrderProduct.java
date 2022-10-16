package com.github.order.orders.domain;

import com.github.order.common.BaseEntity;
import com.github.order.products.domain.Product;
import com.github.order.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "orders_products")
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "orders_products_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_orders_products_to_order"))
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_orders_products_to_products"))
    private Product product;

    @OneToOne(fetch = LAZY, cascade = {PERSIST, REMOVE})
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "fk_orders_products_to_reviews"), nullable = true)
    private Review review;

    @Column(nullable = false)
    private Long quantity;

    public OrderProduct(Order order, Product product, Long quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public static OrderProduct createOrderProduct(Order order, Product product, Long quantity) {
        OrderProduct orderProduct = new OrderProduct(order, product, quantity);
        order.addOrderProduct(orderProduct);
        return orderProduct;
    }

    public void restoreStock() {
        this.product.addQuantity(this.quantity);
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Long totalPrice() {
        return product.getPrice() * this.quantity;
    }



}
