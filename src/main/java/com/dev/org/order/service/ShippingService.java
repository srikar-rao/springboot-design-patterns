package com.dev.org.order.service;

import com.dev.org.order.model.order.Order;
import com.dev.org.order.model.shipping.ShippingResponse;

public interface ShippingService {

    ShippingResponse scheduleShipping(Order order);

}
