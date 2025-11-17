package com.dev.org.loan.strategy;

import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * Strategy interface for the Strategy Pattern.
 * Defines a contract for calculating the interest rate for a loan.
 */
public interface InterestRateStrategy {
    double calculate(LoanApplicationRequest request);
}
