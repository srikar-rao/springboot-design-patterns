package com.dev.org.order.model.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.dev.org.order.model.common.PriceSummary;
import com.dev.org.order.model.shipping.Shipment;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record OrderResponse(UUID orderId,
                            String status,
                            List<Product> products,
                            InvoiceDetails invoice,
                            List<Shipment> shipments) {


    public record Product(String id,
                          String name,
                          double unitPrice,
                          int quantity) {

    }

    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    public record InvoiceDetails(String invoiceId,
                                 String paymentStatus,
                                 PriceSummary priceSummary,
                                 Optional<LocalDate> paymentDue) {
    }

}
