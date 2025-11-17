package com.dev.org.discount.rule;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;

import java.util.List;

/**
 * Implements the Null Object pattern.
 * This rule does nothing and returns a zero discount.
 * It is used to gracefully handle cases where no other discount is applicable,
 * avoiding null checks in the client code.
 */
public class NoDiscountRule implements DiscountRule {

    @Override
    public double apply(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts) {
        // Does nothing, returns 0 discount.
        return 0.0;
    }
}
