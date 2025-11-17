package com.dev.org.order.model.product;

import java.util.List;

public sealed interface Product {

    String productId();

    String name();

    record Single(String productId,
                  String name,
                  double price) implements Product {

    }

    record Bundle(String productId,
                  String name,
                  double originalPrice,
                  double discountedPrice,
                  List<Single> items) implements Product {

    }

}
