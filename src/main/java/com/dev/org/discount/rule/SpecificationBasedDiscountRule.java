package com.dev.org.discount.rule;

import com.dev.org.discount.model.Book;
import com.dev.org.discount.model.DiscountResponse;
import com.dev.org.discount.specification.DiscountSpecification;

import java.util.List;

/**
 * A DiscountRule that uses the Specification pattern to determine eligibility.
 * This class demonstrates how to combine the Rule and Specification patterns.
 * It applies a discount only if the given specification is satisfied.
 */
public class SpecificationBasedDiscountRule implements DiscountRule {

    private final DiscountSpecification specification;
    private final double discountAmount;
    private final String discountType;
    private final String description;

    public SpecificationBasedDiscountRule(DiscountSpecification specification, double discountAmount, String discountType, String description) {
        this.specification = specification;
        this.discountAmount = discountAmount;
        this.discountType = discountType;
        this.description = description;
    }

    @Override
    public double apply(Book book, List<DiscountResponse.DiscountInfo> appliedDiscounts) {
        if (specification.isSatisfiedBy(book)) {
            appliedDiscounts.add(new DiscountResponse.DiscountInfo(discountType, discountAmount, description));
            return discountAmount;
        }
        return 0.0;
    }
}
