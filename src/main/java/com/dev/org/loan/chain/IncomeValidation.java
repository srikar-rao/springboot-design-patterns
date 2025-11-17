package com.dev.org.loan.chain;

import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.specification.IncomeThresholdSpecification;

/**
 * A concrete handler that validates the applicant's income.
 */
public class IncomeValidation implements ValidationHandler {

    private ValidationHandler nextHandler;

    @Override
    public void setNextHandler(ValidationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public boolean validate(LoanApplicationRequest request) {
        if (!new IncomeThresholdSpecification().isSatisfiedBy(request)) {
            return false;
        }
        return nextHandler == null || nextHandler.validate(request);
    }
}
