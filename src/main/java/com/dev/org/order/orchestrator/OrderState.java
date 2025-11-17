package com.dev.org.order.orchestrator;

import com.dev.org.order.model.common.PriceSummary;
import com.dev.org.order.model.invoice.Invoice;
import com.dev.org.order.model.order.CreateOrderCommand;
import com.dev.org.order.model.order.Order;
import com.dev.org.order.model.shipping.Shipment;

import java.util.List;

public sealed interface OrderState {

    record Placed(CreateOrderCommand request) implements OrderState {

    }

    record Validated(Order order) implements OrderState {

    }

    record Priced(Order order,
                  PriceSummary priceSummary) implements OrderState {

    }

    record Invoiced(Order order,
                    Invoice invoice) implements OrderState {

    }

    record Shipped(Order order,
                   Invoice invoice,
                   List<Shipment> shipments) implements OrderState {

    }

    record Fulfilled(Order order,
                     Invoice invoice,
                     List<Shipment> shipments) implements OrderState {

    }

}
