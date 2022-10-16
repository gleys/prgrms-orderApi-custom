package com.github.order.review.domain;

import com.github.order.common.BaseEntity;
import com.github.order.orders.domain.OrderProduct;
import com.github.order.products.domain.Product;
import com.github.order.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_reviews_to_users"))
    private User user;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_reviews_to_products"))
    private Product product;

    public static void write(User user, String content, OrderProduct orderProduct) {
        Review review = new Review();
        review.user = user;
        review.content = content;
        review.product = orderProduct.getProduct();

        orderProduct.setReview(review);
    }


}
