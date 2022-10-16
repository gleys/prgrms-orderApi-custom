package com.github.order.products.domain;

import com.github.order.common.BaseEntity;
import com.github.order.review.domain.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long quantity;

    @Column
    private String details;

    @Column
    @ColumnDefault("0")
    private int reviewCount;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

    public static Product createProduct(String name, Long price, Long quantity, String details) {
        Product product = new Product();
        product.name = name;
        product.price = price;
        product.quantity = quantity;
        product.details = details;

        return product;
    }

    public void changePrice(Long price) {
        this.price = price;
    }

    public void addQuantity(Long quantity) {
        this.quantity += quantity;
    }

    public void subQuantity(Long quantity) {
        this.quantity -= quantity;
    }

    public void addReviewCount() {
        this.reviewCount ++;
    }

    public void subReviewCount() {
        this.reviewCount --;
    }


}
