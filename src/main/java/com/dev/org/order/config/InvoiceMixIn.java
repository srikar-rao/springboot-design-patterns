package com.dev.org.order.config;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.dev.org.order.model.invoice.Invoice;
import org.springframework.boot.jackson.JsonMixin;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION,
        defaultImpl = Invoice.Paid.class
)
@JsonSubTypes({
        @JsonSubTypes.Type(Invoice.Paid.class),
        @JsonSubTypes.Type(Invoice.Unpaid.class),
})
@JsonMixin(Invoice.class)
public class InvoiceMixIn {
}
