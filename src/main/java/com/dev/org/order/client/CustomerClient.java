package com.dev.org.order.client;

import com.dev.org.order.model.customer.Customer;

public interface CustomerClient {

    Customer getCustomer(String customerId);

}
