package com.dev.org.order.model.shipping;

import java.util.List;
import java.util.UUID;

public record ShippingRequest(UUID orderId,
                              Recipient recipient,
                              List<ShipmentItem> shipmentItems) {
}
