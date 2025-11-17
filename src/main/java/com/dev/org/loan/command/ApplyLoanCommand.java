package com.dev.org.loan.command;

import com.dev.org.loan.service.LoanService;
import com.dev.org.loan.model.LoanApplicationRequest;

/**
 * A concrete command for applying for a loan.
 * This command encapsulates the action of initiating a loan application.
 */
public class ApplyLoanCommand implements LoanCommand {

    private final LoanService loanService;
    private final LoanApplicationRequest request;

    public ApplyLoanCommand(LoanService loanService, LoanApplicationRequest request) {
        this.loanService = loanService;
        this.request = request;
    }

    @Override
    public void execute() {
        loanService.processLoanApplication(request);
    }
}
