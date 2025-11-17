package com.dev.org.order.service;

import com.dev.org.order.model.common.PriceSummary;
import com.dev.org.order.model.invoice.Invoice;
import com.dev.org.order.model.order.Order;

public interface PaymentBillingService {

    Invoice processPayment(Order order, PriceSummary priceSummary);

}
