package com.dev.org.order.client;

import com.dev.org.order.model.payment.PaymentRequest;
import com.dev.org.order.model.payment.PaymentStatus;

public interface PaymentClient {

    PaymentStatus process(PaymentRequest request);

}
