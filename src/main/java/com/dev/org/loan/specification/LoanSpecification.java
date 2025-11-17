package com.dev.org.loan.specification;

import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * Specification interface for the Specification Pattern.
 * Defines a contract for checking if a loan application satisfies a specific business rule.
 */
public interface LoanSpecification {
    boolean isSatisfiedBy(LoanApplicationRequest request);
}
