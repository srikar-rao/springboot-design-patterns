package com.dev.org.discount.chain;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;

import java.util.List;

/**
 * Defines the handler interface for the Chain of Responsibility pattern.
 * Each handler in the chain is responsible for applying a discount.
 */
public interface DiscountHandler {
    void setNextHandler(DiscountHandler nextHandler);
    double applyDiscount(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts);
}
