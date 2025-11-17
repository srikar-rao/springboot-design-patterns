package com.dev.org.discount.rule;

import com.dev.org.discount.composite.DiscountComponent;

/**
 * Represents the Rule pattern.
 * This interface defines the contract for an individual discount rule.
 * It extends DiscountComponent to act as a leaf in the Composite structure.
 */
public interface DiscountRule extends DiscountComponent {
}
