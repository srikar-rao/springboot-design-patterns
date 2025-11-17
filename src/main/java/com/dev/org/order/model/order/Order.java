package com.dev.org.order.model.order;

import com.dev.org.order.model.customer.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public record Order(UUID orderId,
                    Customer customer,
                    OrderItem orderItem,
                    LocalDateTime createdAt) {
}
