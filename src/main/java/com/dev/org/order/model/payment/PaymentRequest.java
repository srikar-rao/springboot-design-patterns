package com.dev.org.order.model.payment;

import java.util.UUID;

public record PaymentRequest(String customerId,
                             UUID orderId,
                             double amount) {
}
