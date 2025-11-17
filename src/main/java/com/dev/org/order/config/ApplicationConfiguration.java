package com.dev.org.order.config;

import com.dev.org.order.client.BillingClient;
import com.dev.org.order.client.CustomerClient;
import com.dev.org.order.client.PaymentClient;
import com.dev.org.order.client.ProductClient;
import com.dev.org.order.client.ShippingClient;
import com.dev.org.order.client.impl.BillingServiceClient;
import com.dev.org.order.client.impl.CustomerServiceClient;
import com.dev.org.order.client.impl.PaymentServiceClient;
import com.dev.org.order.client.impl.ProductServiceClient;
import com.dev.org.order.client.impl.ShippingServiceClient;
import com.dev.org.order.orchestrator.OrderOrchestrator;
import com.dev.org.order.orchestrator.impl.OrderOrchestratorImpl;
import com.dev.org.order.service.OrderService;
import com.dev.org.order.service.PaymentBillingService;
import com.dev.org.order.service.PriceCalculator;
import com.dev.org.order.service.RequestValidatorService;
import com.dev.org.order.service.ShippingService;
import com.dev.org.order.service.impl.OrderServiceImpl;
import com.dev.org.order.service.impl.PaymentBillingServiceImpl;
import com.dev.org.order.service.impl.PriceCalculatorImpl;
import com.dev.org.order.service.impl.RequestValidatorServiceImpl;
import com.dev.org.order.service.impl.ShippingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApplicationConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);
    private final RestClient.Builder builder;

    public ApplicationConfiguration(RestClient.Builder builder) {
        this.builder = builder.requestInterceptor(new LoggingInterceptor()); // builder might have a list of interceptors. so just add it once.
    }

    @Bean
    public ProductClient productClient(@Value("mockurl:8080") String baseUrl) {
        return new ProductServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public CustomerClient customerClient(@Value("mockurl:8080") String baseUrl) {
        return new CustomerServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public PaymentClient paymentClient(@Value("mockurl:8080") String baseUrl) {
        return new PaymentServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public BillingClient billingClient(@Value("mockurl:8080") String baseUrl) {
        return new BillingServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public ShippingClient shippingClient(@Value("mockurl:8080") String baseUrl) {
        return new ShippingServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public RequestValidatorService requestValidatorService(ProductClient productClient, CustomerClient customerClient) {
        return new RequestValidatorServiceImpl(productClient, customerClient);
    }

    @Bean
    public PriceCalculator priceCalculator() {
        return new PriceCalculatorImpl();
    }

    @Bean
    public PaymentBillingService paymentBillingService(PaymentClient paymentClient, BillingClient billingClient) {
        return new PaymentBillingServiceImpl(paymentClient, billingClient);
    }

    @Bean
    public ShippingService shippingService(ShippingClient shippingClient) {
        return new ShippingServiceImpl(shippingClient);
    }

    @Bean
    public OrderOrchestrator orderOrchestrator(RequestValidatorService validatorService,
                                               PriceCalculator priceCalculator,
                                               PaymentBillingService paymentBillingService,
                                               ShippingService shippingService) {
        return new OrderOrchestratorImpl(
                validatorService,
                priceCalculator,
                paymentBillingService,
                shippingService
        );
    }

    @Bean
    public OrderService order(OrderOrchestrator orderOrchestrator) {
        return new OrderServiceImpl(orderOrchestrator);
    }

    private RestClient buildRestClient(String baseUrl) {
        log.info("base url: {}", baseUrl);
        return this.builder.baseUrl(baseUrl)
                .build();
    }

}
