package com.github.order.orders.Controller;

import com.github.order.orders.application.OrderService;
import com.github.order.orders.application.dto.OrderResponse;
import com.github.utils.ResponseForm.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.utils.ResponseForm.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("{id}")
    public Result<OrderResponse> findById(@PathVariable Long id) {
        return success(orderService.findById(id));
    }

    @GetMapping
    public Result<List<OrderResponse>> findByAll(@RequestParam Long offset, @RequestParam int limit) {
        return success(orderService.findAll(1L, offset, limit));
    }

}
