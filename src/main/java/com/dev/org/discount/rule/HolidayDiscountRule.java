package com.dev.org.discount.rule;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;

import java.util.List;

/**
 * A concrete implementation of a DiscountRule.
 * This rule applies a fixed discount for a holiday promotion.
 * It demonstrates the Rule pattern by encapsulating a specific piece of business logic.
 */
public class HolidayDiscountRule implements DiscountRule {

    @Override
    public double apply(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts) {
        double discountAmount = 10.00; // Fixed holiday discount
        appliedDiscounts.add(new DiscountResponse.DiscountInfo("HolidayDiscount", discountAmount, "Seasonal offer applied"));
        return discountAmount;
    }
}
