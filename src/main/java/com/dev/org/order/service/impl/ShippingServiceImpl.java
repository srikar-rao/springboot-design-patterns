package com.dev.org.order.service.impl;

import com.dev.org.order.client.ShippingClient;
import com.dev.org.order.model.order.Order;
import com.dev.org.order.model.product.Product.*;
import com.dev.org.order.model.shipping.Recipient;
import com.dev.org.order.model.shipping.ShipmentItem;
import com.dev.org.order.model.shipping.ShippingRequest;
import com.dev.org.order.model.shipping.ShippingResponse;
import com.dev.org.order.service.ShippingService;

import java.util.List;

public class ShippingServiceImpl implements ShippingService {

    private final ShippingClient shippingClient;

    public ShippingServiceImpl(ShippingClient shippingClient) {
        this.shippingClient = shippingClient;
    }

    @Override
    public ShippingResponse scheduleShipping(Order order) {
        var request = this.toShippingRequest(order);
        return this.shippingClient.schedule(request);
    }

    private ShippingRequest toShippingRequest(Order order) {
        var recipient = new Recipient(order.customer().name(), order.customer().address());
        var quantity = order.orderItem().quantity();
        var items = switch (order.orderItem().product()) {
            case Single single -> List.of(new ShipmentItem(single.productId(), quantity));
            case Bundle bundle -> bundle.items()
                    .stream()
                    .map(Single::productId)
                    .map(id -> new ShipmentItem(id, quantity))
                    .toList();
        };
        return new ShippingRequest(order.orderId(), recipient, items);
    }
}
