package com.dev.org.order.client.impl;

import com.dev.org.order.client.ProductClient;
import com.dev.org.order.exception.ApplicationExceptions;
import com.dev.org.order.model.product.ProductStatus;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Supplier;

public class ProductServiceClient extends AbstractServiceClient implements ProductClient {

    private final RestClient restClient;

    public ProductServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "product-service";
    }

    @Override
    public ProductStatus getProduct(String productId) {
        var errorMap = Map.<Integer, Supplier<ProductStatus>>of(
                404, () -> ApplicationExceptions.productNotFound(productId)
        );
        return this.executeRequest(
                () -> this.restClient.get()
                        .uri("/{productId}", productId)
                        .retrieve()
                        .body(ProductStatus.class),
                errorMap
        );
    }

}
