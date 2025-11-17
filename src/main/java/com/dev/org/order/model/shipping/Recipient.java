package com.dev.org.order.model.shipping;

import com.dev.org.order.model.common.Address;

public record Recipient(String name,
                        Address address) {
}
