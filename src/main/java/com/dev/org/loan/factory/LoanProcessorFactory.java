package com.dev.org.loan.factory;

import com.dev.org.loan.model.LoanType;
import com.dev.org.loan.template.BusinessLoanProcessor;
import com.dev.org.loan.template.HomeLoanProcessor;
import com.dev.org.loan.template.LoanNullProcessor;
import com.dev.org.loan.template.LoanProcessor;
import com.dev.org.loan.template.VehicleLoanProcessor;
import org.springframework.stereotype.Component;

/**
 * Factory for creating LoanProcessor instances based on the loan type.
 */
@Component
public class LoanProcessorFactory {

    public LoanProcessor getProcessor(LoanType loanType) {
        if (loanType == null) {
            return new LoanNullProcessor();
        }
        return switch (loanType) {
            case HOME -> new HomeLoanProcessor();
            case VEHICLE -> new VehicleLoanProcessor();
            case BUSINESS -> new BusinessLoanProcessor();
        };
    }
}
