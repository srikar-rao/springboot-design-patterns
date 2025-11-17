package com.dev.org.loan.template;

import com.dev.org.loan.chain.CreditScoreValidation;
import com.dev.org.loan.chain.IncomeValidation;
import com.dev.org.loan.chain.PropertyValidation;
import com.dev.org.loan.chain.ValidationHandler;
import com.dev.org.loan.model.LoanStatus;
import com.dev.org.loan.strategy.InterestRateStrategy;
import com.dev.org.loan.strategy.LtvBasedInterestStrategy;

/**
 * Concrete implementation of LoanProcessor for home loans.
 */
public class HomeLoanProcessor implements LoanProcessor {

    private final InterestRateStrategy interestRateStrategy = new LtvBasedInterestStrategy();

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
        ValidationHandler propertyValidation = new PropertyValidation();
        ValidationHandler creditScoreValidation = new CreditScoreValidation();

        validationChain.setNextHandler(propertyValidation);
        propertyValidation.setNextHandler(creditScoreValidation);

        if (validationChain.validate(submitted.request())) {
            return new LoanStatus.Reviewed(submitted.request(), "Initial validation passed.");
        }
        return new LoanStatus.Denied("Initial validation failed.");
    }

    @Override
    public LoanStatus handle(LoanStatus.Reviewed reviewed) {
        double interestRate = interestRateStrategy.calculate(reviewed.request());
        double approvedAmount = reviewed.request().getRequestedAmount() * 0.9; // Approve 90% of requested amount
        return new LoanStatus.Approved(approvedAmount, interestRate);
    }
}
