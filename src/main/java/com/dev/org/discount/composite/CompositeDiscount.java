package com.dev.org.discount.composite;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Composite pattern.
 * This class holds a collection of other DiscountComponent objects (children).
 * It allows grouping multiple discounts and applying them together as a single unit.
 */
public class CompositeDiscount implements DiscountComponent {

    private final List<DiscountComponent> discounts = new ArrayList<>();

    public void addDiscount(DiscountComponent discount) {
        discounts.add(discount);
    }

    public void removeDiscount(DiscountComponent discount) {
        discounts.remove(discount);
    }

    @Override
    public double apply(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts) {
        return discounts.stream()
                .mapToDouble(discount -> discount.apply(book, appliedDiscounts))
                .sum();
    }
}
