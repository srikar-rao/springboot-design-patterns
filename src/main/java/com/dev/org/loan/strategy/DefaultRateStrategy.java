package com.dev.org.loan.strategy;

import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * A concrete strategy that provides a default, fixed interest rate.
 */
public class DefaultRateStrategy implements InterestRateStrategy {

    private static final double DEFAULT_RATE = 8.0;

    @Override
    public double calculate(LoanApplicationRequest request) {
        return DEFAULT_RATE;
    }
}
