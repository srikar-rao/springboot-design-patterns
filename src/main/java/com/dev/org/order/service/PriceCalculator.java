package com.dev.org.order.service;

import com.dev.org.order.model.common.PriceSummary;
import com.dev.org.order.model.order.Order;

public interface PriceCalculator {

    PriceSummary calculate(Order order);

}
