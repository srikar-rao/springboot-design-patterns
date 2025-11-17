package com.dev.org.order.client;

import com.dev.org.order.model.product.ProductStatus;

public interface ProductClient {

    ProductStatus getProduct(String productId);

}
