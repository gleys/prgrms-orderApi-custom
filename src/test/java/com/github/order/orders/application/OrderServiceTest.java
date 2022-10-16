package com.github.order.orders.application;

import com.github.order.orders.application.dto.order.OrderResponse;
import com.github.order.orders.repository.OrderRepository;
import com.github.order.products.application.ProductService;
import com.github.order.user.application.UserService;
import com.github.order.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@Rollback(false)
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductService productService;

    @PersistenceContext
    EntityManager em;

//    @BeforeEach
//    void beforeEach() {
//        ProductForm item1 = new ProductForm("item1", 15000L, 100L, "item1");
//        ProductForm item2 = new ProductForm("item2", 7500L, 50L, "item2");
//        ProductForm item3 = new ProductForm("item3", 3250L, 50L, "item3");
//        ProductForm item4 = new ProductForm("item4", 23310L, 50L, "item4");
//        ProductForm item5 = new ProductForm("item5", 1601L, 50L, "item5");
//        ProductForm item6 = new ProductForm("item6", 1557L, 50L, "item6");
//
//        Long product1 = productService.create(item1);
//        Long product2 = productService.create(item2);
//        Long product3 = productService.create(item3);
//        Long product4 = productService.create(item4);
//
//        Long userId = userService.register(registerForm);
//
//        Long order1 = orderService.create(userId);
//        Long order2 = orderService.create(userId);
//        Long order3 = orderService.create(userId);
//        Long order4 = orderService.create(userId);
//
//        log.info("order {}, {}, {}, {}", order1, order2, order3, order4);
//
//        orderService.addProduct(order1, product1, 17L);
//        orderService.addProduct(order1, product2, 13L);
//
//        orderService.addProduct(order2, product2, 1L);
//        orderService.addProduct(order2, product3, 2L);
//        orderService.addProduct(order2, product4, 10L);
//
//        orderService.addProduct(order3, product1, 17L);
//
//        orderService.addProduct(order4, product1, 5L);
//        orderService.addProduct(order4, product2, 6L);
//        orderService.addProduct(order4, product3, 3L);
//        orderService.addProduct(order4, product4, 3L);
//
//    }

    @Test
    void create() {
        List<OrderResponse> all = orderService.findAll(1L, 0L, 5);
        log.info("orderResponse: {}", all);


    }

    @Test
    void addProduct() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void submit() {
    }

    @Test
    void accept() {
    }

    @Test
    void cancel() {
    }

    @Test
    void shipping() {
    }

    @Test
    void complete() {
    }
}