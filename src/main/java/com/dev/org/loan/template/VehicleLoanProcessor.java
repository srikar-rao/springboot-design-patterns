package com.dev.org.loan.template;

import com.dev.org.loan.chain.CreditScoreValidation;
import com.dev.org.loan.chain.IncomeValidation;
import com.dev.org.loan.chain.ValidationHandler;
import com.dev.org.loan.model.LoanStatus;
import com.dev.org.loan.strategy.DefaultRateStrategy;
import com.dev.org.loan.strategy.InterestRateStrategy;

/**
 * Concrete implementation of LoanProcessor for vehicle loans.
 */
public class VehicleLoanProcessor implements LoanProcessor {

    private final InterestRateStrategy interestRateStrategy = new DefaultRateStrategy();

    @Override
    public LoanStatus process(LoanStatus currentStatus) {
        return switch (currentStatus) {
            case LoanStatus.Submitted submitted -> process(handle(submitted));
            case LoanStatus.Reviewed reviewed -> process(handle(reviewed));
            case LoanStatus.Approved approved -> approved;
            case LoanStatus.Denied denied -> denied;
        };
    }

    @Override
    public LoanStatus handle(LoanStatus.Submitted submitted) {
        ValidationHandler validationChain = new IncomeValidation();
        validationChain.setNextHandler(new CreditScoreValidation());

        if (validationChain.validate(submitted.request())) {
            return new LoanStatus.Reviewed(submitted.request(), "Initial validation passed.");
        }
        return new LoanStatus.Denied("Validation failed for vehicle loan.");
    }

    @Override
    public LoanStatus handle(LoanStatus.Reviewed reviewed) {
        double interestRate = interestRateStrategy.calculate(reviewed.request());
        double approvedAmount = reviewed.request().getRequestedAmount() * 0.8; // Approve 80%
        return new LoanStatus.Approved(approvedAmount, interestRate);
    }
}
