package com.dev.org.order.model.shipping;

import java.time.LocalDate;

public record TrackingDetails(String carrier,
                              String trackingNumber,
                              LocalDate estimatedDeliveryDate) {
}
