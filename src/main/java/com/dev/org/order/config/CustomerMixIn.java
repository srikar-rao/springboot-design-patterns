package com.dev.org.order.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.dev.org.order.model.customer.Customer;
import org.springframework.boot.jackson.JsonMixin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Customer.Regular.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Customer.Regular.class),
        @JsonSubTypes.Type(Customer.Business.class),
})
@JsonMixin(Customer.class)
public class CustomerMixIn {
}
