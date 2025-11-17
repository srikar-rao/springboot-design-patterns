package com.dev.org.order.service.impl;

import com.dev.org.order.model.order.OrderRequest;
import com.dev.org.order.model.order.OrderResponse;
import com.dev.org.order.orchestrator.OrderOrchestrator;
import com.dev.org.order.orchestrator.OrderState;
import com.dev.org.order.service.OrderService;
import com.dev.org.order.util.DomainDtoMapper;

public class OrderServiceImpl implements OrderService {

    private final OrderOrchestrator orderOrchestrator;

    public OrderServiceImpl(OrderOrchestrator orderOrchestrator) {
        this.orderOrchestrator = orderOrchestrator;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        var command = DomainDtoMapper.toCreateOrderCommand(request);
        var placedOrderState = new OrderState.Placed(command);
        var orderState = this.orderOrchestrator.orchestrate(placedOrderState);
        return switch (orderState) {
            case OrderState.Fulfilled fulfilled ->
                    DomainDtoMapper.toOrderResponse(fulfilled.order(), fulfilled.invoice(), fulfilled.shipments());
            default -> throw new IllegalStateException("Unexpected value: " + orderState); // should not happen
        };
    }
}
