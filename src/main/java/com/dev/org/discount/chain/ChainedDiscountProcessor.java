package com.dev.org.discount.chain;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;
import com.dev.org.discount.rule.DiscountRule;

import java.util.List;

/**
 * A concrete handler in the Chain of Responsibility pattern.
 * This processor applies a specific DiscountRule and then passes the request
 * to the next handler in the chain.
 */
public class ChainedDiscountProcessor implements DiscountHandler {

    private DiscountHandler nextHandler;
    private final DiscountRule discountRule;

    public ChainedDiscountProcessor(DiscountRule discountRule) {
        this.discountRule = discountRule;
    }

    @Override
    public void setNextHandler(DiscountHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public double applyDiscount(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts) {
        double discount = discountRule.apply(book, appliedDiscounts);
        if (nextHandler != null) {
            discount += nextHandler.applyDiscount(book, appliedDiscounts);
        }
        return discount;
    }
}
