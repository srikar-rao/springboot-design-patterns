package com.dev.org.order.service.impl;

import com.dev.org.order.client.CustomerClient;
import com.dev.org.order.client.ProductClient;
import com.dev.org.order.exception.ApplicationExceptions;
import com.dev.org.order.model.order.CreateOrderCommand;
import com.dev.org.order.model.order.Order;
import com.dev.org.order.model.order.OrderItem;
import com.dev.org.order.model.product.Product;
import com.dev.org.order.model.product.ProductStatus.*;
import com.dev.org.order.service.RequestValidatorService;

import java.time.LocalDateTime;

public class RequestValidatorServiceImpl implements RequestValidatorService {

    private final ProductClient productClient;
    private final CustomerClient customerClient;

    public RequestValidatorServiceImpl(ProductClient productClient, CustomerClient customerClient) {
        this.productClient = productClient;
        this.customerClient = customerClient;
    }

    @Override
    public Order validate(CreateOrderCommand request) {
        var product = this.getProduct(request.productId());
        var customer = this.customerClient.getCustomer(request.customerId());
        var orderItem = new OrderItem(product, request.quantity());
        return new Order(request.orderId(), customer, orderItem, LocalDateTime.now());
    }

    private Product getProduct(String productId) {
        return switch (this.productClient.getProduct(productId)) {
            case Active active -> active.product();
            case Discontinued discontinued -> ApplicationExceptions.discontinuedProduct(discontinued);
        };
    }

}
