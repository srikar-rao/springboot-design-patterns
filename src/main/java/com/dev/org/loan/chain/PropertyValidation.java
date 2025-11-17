package com.dev.org.loan.chain;

import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.specification.LtvRatioSpecification;

/**
 * A concrete handler that validates the loan-to-value ratio.
 */
public class PropertyValidation implements ValidationHandler {

    private ValidationHandler nextHandler;

    @Override
    public void setNextHandler(ValidationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public boolean validate(LoanApplicationRequest request) {
        if (!new LtvRatioSpecification().isSatisfiedBy(request)) {
            return false;
        }
        return nextHandler == null || nextHandler.validate(request);
    }
}
