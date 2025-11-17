package com.dev.org.loan.strategy;

import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * A concrete strategy that calculates the interest rate based on the loan-to-value (LTV) ratio.
 */
public class LtvBasedInterestStrategy implements InterestRateStrategy {

    @Override
    public double calculate(LoanApplicationRequest request) {
        double ltv = request.getRequestedAmount() / request.getPropertyValue();
        if (ltv > 0.8) {
            return 7.5; // Higher risk
        } else if (ltv > 0.6) {
            return 6.5;
        } else {
            return 5.5; // Lower risk
        }
    }
}
