package com.dev.org.discount.specification;

import com.dev.org.discount.model.Book;

/**
 * A concrete implementation of the Specification pattern.
 * This specification checks if the book's price qualifies for a bulk discount.
 */
public class BulkPurchaseSpecification implements DiscountSpecification {

    private static final double BULK_PURCHASE_THRESHOLD = 100.0;

    @Override
    public boolean isSatisfiedBy(Book book) {
        return book.getBasePrice() >= BULK_PURCHASE_THRESHOLD;
    }
}
