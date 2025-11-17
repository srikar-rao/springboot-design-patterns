package com.dev.org.order.client;

import com.dev.org.order.model.invoice.Invoice;
import com.dev.org.order.model.invoice.InvoiceRequest;

public interface BillingClient {

    Invoice createInvoice(InvoiceRequest request);

}
