package com.dev.org.order.controller;

import com.dev.org.order.model.order.OrderRequest;
import com.dev.org.order.model.order.OrderResponse;
import com.dev.org.order.service.OrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService order;

    public OrderController(OrderService order) {
        this.order = order;
    }


    @PostMapping("orders")
    public OrderResponse placeOrder(@Validated @RequestBody OrderRequest orderRequest) {
        return this.order.placeOrder(orderRequest);
    }

}
