package com.dev.org.order.client;

import com.dev.org.order.model.shipping.ShippingRequest;
import com.dev.org.order.model.shipping.ShippingResponse;

public interface ShippingClient {

    ShippingResponse schedule(ShippingRequest request);

}
