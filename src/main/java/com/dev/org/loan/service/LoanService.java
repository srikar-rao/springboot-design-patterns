package com.dev.org.loan.service;

import com.dev.org.loan.factory.LoanProcessorFactory;
import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.model.LoanResponse;
import com.dev.org.loan.model.LoanStatus;
import com.dev.org.loan.template.LoanProcessor;
import com.dev.org.loan.util.MockDataProvider;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private final LoanProcessorFactory loanProcessorFactory;
    private final MockDataProvider mockDataProvider;

    public LoanService(LoanProcessorFactory loanProcessorFactory, MockDataProvider mockDataProvider) {
        this.loanProcessorFactory = loanProcessorFactory;
        this.mockDataProvider = mockDataProvider;
    }

    public LoanResponse processLoanApplication(LoanApplicationRequest request) {
        LoanProcessor processor = loanProcessorFactory.getProcessor(request.getLoanType());
        LoanStatus initialState = new LoanStatus.Submitted(request);
        LoanStatus finalState = processor.process(initialState);

        return createLoanResponse(finalState);
    }

    private LoanResponse createLoanResponse(LoanStatus finalState) {
        String applicationId = mockDataProvider.generateApplicationId();
        return switch (finalState) {
            case LoanStatus.Approved approved -> new LoanResponse(applicationId, "APPROVED", approved.approvedAmount(), approved.interestRate(), "Loan approved based on eligibility rules");
            case LoanStatus.Denied denied -> new LoanResponse(applicationId, "DENIED", 0, 0, denied.reason());
            default -> new LoanResponse(applicationId, "ERROR", 0, 0, "An unexpected error occurred during processing.");
        };
    }
}
