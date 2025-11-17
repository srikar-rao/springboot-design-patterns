package com.dev.org.order.model.order;

import com.dev.org.order.model.product.Product;

public record OrderItem(Product product,
                        int quantity) {
}
