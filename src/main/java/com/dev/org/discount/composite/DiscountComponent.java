package com.dev.org.discount.composite;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;

import java.util.List;

/**
 * Component interface for the Composite Pattern.
 * Defines the contract for both leaf-level discounts (rules) and composite discounts.
 * This allows clients to treat individual discounts and compositions of discounts uniformly.
 */
public interface DiscountComponent {
    double apply(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts);
}
