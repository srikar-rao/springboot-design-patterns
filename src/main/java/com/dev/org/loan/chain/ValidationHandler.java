package com.dev.org.loan.chain;

import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * Handler interface for the Chain of Responsibility Pattern.
 * Defines a contract for a validation step in the loan application process.
 */
public interface ValidationHandler {
    void setNextHandler(ValidationHandler nextHandler);
    boolean validate(LoanApplicationRequest request);
}
