package com.dev.org.loan.specification;

import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * A concrete specification that checks if the loan-to-value (LTV) ratio is within an acceptable limit.
 */
public class LtvRatioSpecification implements LoanSpecification {

    private static final double MAX_LTV_RATIO = 0.8; // 80%

    @Override
    public boolean isSatisfiedBy(LoanApplicationRequest request) {
        if (request.getPropertyValue() <= 0) {
            return false;
        }
        return (request.getRequestedAmount() / request.getPropertyValue()) <= MAX_LTV_RATIO;
    }
}
