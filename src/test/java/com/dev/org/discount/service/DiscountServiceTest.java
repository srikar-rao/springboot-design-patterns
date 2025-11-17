package com.dev.org.discount.service;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountServiceTest {

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountService();
    }

    @Test
    void shouldApplyCompositeDiscountCorrectly() {
        Book book = new Book("Clean Code", 50.00, "COMPOSITE");
        DiscountResponse response = discountService.calculateDiscount(book);

        assertEquals(50.00, response.getOriginalPrice());
        assertEquals(35.00, response.getFinalPrice()); // 50 - 5 (name) - 10 (holiday) = 35
        assertEquals(2, response.getDiscountsApplied().size());
    }

    @Test
    void shouldApplyChainedDiscountCorrectly() {
        Book book = new Book("Clean Code", 50.00, "CHAINED");
        DiscountResponse response = discountService.calculateDiscount(book);

        assertEquals(50.00, response.getOriginalPrice());
        assertEquals(35.00, response.getFinalPrice()); // 50 - 5 (name) - 10 (holiday) = 35
        assertEquals(2, response.getDiscountsApplied().size());
    }

    @Test
    void shouldApplyNoDiscountCorrectly() {
        Book book = new Book("Another Book", 50.00, "NONE");
        DiscountResponse response = discountService.calculateDiscount(book);

        assertEquals(50.00, response.getOriginalPrice());
        assertEquals(50.00, response.getFinalPrice());
        assertEquals(0, response.getDiscountsApplied().size());
    }

    @Test
    void shouldNotAllowPriceToGoBelowZero() {
        Book book = new Book("Cheap Book", 5.00, "COMPOSITE");
        DiscountResponse response = discountService.calculateDiscount(book);

        assertEquals(5.00, response.getOriginalPrice());
        assertEquals(0.00, response.getFinalPrice());
    }
}
