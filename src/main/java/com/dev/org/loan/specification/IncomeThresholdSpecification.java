package com.dev.org.loan.specification;

import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * A concrete specification that checks if the applicant's income meets a minimum threshold.
 */
public class IncomeThresholdSpecification implements LoanSpecification {

    private static final double MIN_INCOME_THRESHOLD = 30000;

    @Override
    public boolean isSatisfiedBy(LoanApplicationRequest request) {
        return request.getIncome() >= MIN_INCOME_THRESHOLD;
    }
}
