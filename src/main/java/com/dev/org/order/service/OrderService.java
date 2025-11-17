package com.dev.org.order.service;

import com.dev.org.order.model.order.OrderRequest;
import com.dev.org.order.model.order.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

}
