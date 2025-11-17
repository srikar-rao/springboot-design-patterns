package com.dev.org.order.client.impl;

import com.dev.org.order.client.BillingClient;
import com.dev.org.order.model.invoice.Invoice;
import com.dev.org.order.model.invoice.InvoiceRequest;
import com.dev.org.order.model.invoice.InvoiceRequest.*;
import org.springframework.web.client.RestClient;

import java.util.Collections;

public class BillingServiceClient extends AbstractServiceClient implements BillingClient {

    private final RestClient restClient;

    public BillingServiceClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    protected String getServiceName() {
        return "billing-service";
    }

    @Override
    public Invoice createInvoice(InvoiceRequest request) {
        return switch (request) {
            case Paid p -> this.executeRequest("/invoices/paid", request);
            case Unpaid u -> this.executeRequest("/invoices/unpaid", request);
        };
    }

    private Invoice executeRequest(String path, InvoiceRequest request) {
        return this.executeRequest(
                () -> this.restClient.post()
                        .uri(path)
                        .body(request)
                        .retrieve()
                        .body(Invoice.class),
                Collections.emptyMap()
        );
    }

}

