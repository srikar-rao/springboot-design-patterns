package com.dev.org.loan.template;

import com.dev.org.loan.model.LoanStatus;

/**
 * Template Method and State Pattern combination.
 * This interface defines the skeleton of the loan processing algorithm.
 * It uses a default method with Java's pattern matching switch to handle state transitions.
 */
public interface LoanProcessor {

    LoanStatus process(LoanStatus currentStatus);

    LoanStatus handle(LoanStatus.Submitted submitted);

    LoanStatus handle(LoanStatus.Reviewed reviewed);
}
