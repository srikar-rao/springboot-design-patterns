package com.dev.org.loan.chain;

import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * A concrete handler that validates the applicant's credit score (mocked).
 */
public class CreditScoreValidation implements ValidationHandler {

    private ValidationHandler nextHandler;
    private static final int MIN_CREDIT_SCORE = 650;

    @Override
    public void setNextHandler(ValidationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public boolean validate(LoanApplicationRequest request) {
        // In a real application, this would come from a credit bureau service.
        int mockCreditScore = 700;
        if (mockCreditScore < MIN_CREDIT_SCORE) {
            return false;
        }
        return nextHandler == null || nextHandler.validate(request);
    }
}
