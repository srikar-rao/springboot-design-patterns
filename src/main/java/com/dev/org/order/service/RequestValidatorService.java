package com.dev.org.order.service;

import com.dev.org.order.model.order.CreateOrderCommand;
import com.dev.org.order.model.order.Order;

public interface RequestValidatorService {

    Order validate(CreateOrderCommand request);

}
