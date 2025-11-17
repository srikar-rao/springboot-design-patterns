package com.dev.org.order.client.impl;

import com.dev.org.order.client.CustomerClient;
import com.dev.org.order.exception.ApplicationExceptions;
import com.dev.org.order.model.customer.Customer;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.function.Supplier;

public class CustomerServiceClient extends AbstractServiceClient implements CustomerClient {

    private final RestClient restClient;

    public CustomerServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "customer-service";
    }

    @Override
    public Customer getCustomer(String customerId) {
        var errorMap = Map.<Integer, Supplier<Customer>>of(
                404, () -> ApplicationExceptions.customerNotFound(customerId)
        );
        return this.executeRequest(
                () -> this.restClient.get()
                        .uri("/{customerId}", customerId)
                        .retrieve()
                        .body(Customer.class),
                errorMap
        );
    }

}
