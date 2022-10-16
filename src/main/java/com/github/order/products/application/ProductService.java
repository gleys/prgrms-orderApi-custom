package com.github.order.products.application;

import com.github.order.products.domain.Product;
import com.github.order.products.dto.ProductForm;
import com.github.order.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Long create(ProductForm form) {
        String name = form.getName();
        Long price = form.getPrice();
        Long quantity = form.getQuantity();
        String details = form.getDetails();

        Product product = Product.createProduct(name, price, quantity, details);

        productRepository.save(product);
        return product.getId();
    }

    public List<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public void removeItem(Long itemId) {
        productRepository.deleteById(itemId);
    }
}
