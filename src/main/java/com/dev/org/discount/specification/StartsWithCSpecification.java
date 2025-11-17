package com.dev.org.discount.specification;

import com.dev.org.discount.model.Book;

/**
 * A concrete implementation of the Specification pattern.
 * This specification checks if the book's name starts with the letter 'C'.
 */
public class StartsWithCSpecification implements DiscountSpecification {
    @Override
    public boolean isSatisfiedBy(Book book) {
        return book.getBookName() != null && book.getBookName().startsWith("C");
    }
}
