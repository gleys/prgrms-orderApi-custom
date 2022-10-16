package com.github.order.products.dto;

import com.github.order.products.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductForm {

    private String name;
    private Long price;
    private Long quantity;
    private String details;
}
