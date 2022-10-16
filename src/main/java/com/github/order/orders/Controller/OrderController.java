package com.github.order.orders.Controller;

import com.github.order.orders.application.OrderService;
import com.github.order.orders.application.dto.RequestMessage;
import com.github.order.orders.application.dto.ProductRequest;
import com.github.order.orders.application.dto.order.OrderResponse;
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

    //주문생성 (장바구니 상태)
    @PostMapping("create")
    public Result<Long> create() {
        return success(orderService.create(1L));
    }

    //장바구니 상품추가
    @PutMapping
    public Result<Boolean> addProduct(@RequestBody ProductRequest request) {
        return success(orderService.addProduct(request));
    }

    //구매자 결제요청
    @PatchMapping("{id}/submit")
    public Result<Boolean> submit(@PathVariable Long id, @RequestBody(required = false) RequestMessage request) {
        return success(orderService.submit(id, request.getMessage()));
    }

    //판매자가 주문승인
    @PatchMapping("{id}/accept")
    public Result<Boolean> accept(@PathVariable Long id) {
        return success(orderService.accept(id));
    }

    //판매자가 배송처리
    @PatchMapping("{id}/shipping")
    public Result<Boolean> shipping(@PathVariable Long id) {
        return success(orderService.shipping(id));
    }

    //고객이 주문취소
    @PatchMapping("{id}/cancel")
    public Result<Boolean> cancel(@PathVariable Long id, @RequestBody(required = false) RequestMessage request) {
        return success(orderService.cancel(id, request.getMessage()));
    }

    //고객이 배송완료 요청
    @PatchMapping("{id}/complete")
    public Result<Boolean> complete(@PathVariable Long id) {
        return success(orderService.complete(id));
    }

}
