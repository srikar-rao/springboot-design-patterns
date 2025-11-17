package com.dev.org.loan.template;

import com.dev.org.loan.model.LoanStatus;

/**
 * Null Object pattern implementation for LoanProcessor.
 * Used when no specific processor is found for a given loan type.
 */
public class LoanNullProcessor implements LoanProcessor {

    private static final String ERROR_MESSAGE = "Invalid loan type specified.";

    @Override
    public LoanStatus process(LoanStatus currentStatus) {
        if (currentStatus instanceof LoanStatus.Submitted submitted) {
            return handle(submitted);
        }
        return new LoanStatus.Denied(ERROR_MESSAGE);
    }

    @Override
    public LoanStatus handle(LoanStatus.Submitted submitted) {
        return new LoanStatus.Denied(ERROR_MESSAGE);
    }

    @Override
    public LoanStatus handle(LoanStatus.Reviewed reviewed) {
        return new LoanStatus.Denied(ERROR_MESSAGE);
    }
}
