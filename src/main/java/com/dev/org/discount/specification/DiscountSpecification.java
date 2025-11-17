package com.dev.org.discount.specification;

import com.dev.org.discount.model.Book;

/**
 * Defines the Specification pattern interface.
 * This pattern is used to encapsulate business rules about discount eligibility.
 * Each specification can be checked against a Book object.
 */
public interface DiscountSpecification {
    boolean isSatisfiedBy(Book book);
}
