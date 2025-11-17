package com.dev.org.loan.factory;

import com.dev.org.loan.model.LoanType;
import com.dev.org.loan.template.BusinessLoanProcessor;
import com.dev.org.loan.template.HomeLoanProcessor;
import com.dev.org.loan.template.LoanNullProcessor;
import com.dev.org.loan.template.LoanProcessor;
import com.dev.org.loan.template.VehicleLoanProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class LoanProcessorFactoryTest {

    private LoanProcessorFactory factory;

    @BeforeEach
    void setUp() {
        factory = new LoanProcessorFactory();
    }

    @Test
    void shouldReturnHomeLoanProcessorForHomeLoanType() {
        LoanProcessor processor = factory.getProcessor(LoanType.HOME);
        assertInstanceOf(HomeLoanProcessor.class, processor);
    }

    @Test
    void shouldReturnVehicleLoanProcessorForVehicleLoanType() {
        LoanProcessor processor = factory.getProcessor(LoanType.VEHICLE);
        assertInstanceOf(VehicleLoanProcessor.class, processor);
    }

    @Test
    void shouldReturnBusinessLoanProcessorForBusinessLoanType() {
        LoanProcessor processor = factory.getProcessor(LoanType.BUSINESS);
        assertInstanceOf(BusinessLoanProcessor.class, processor);
    }

    @Test
    void shouldReturnNullProcessorForNullLoanType() {
        LoanProcessor processor = factory.getProcessor(null);
        assertInstanceOf(LoanNullProcessor.class, processor);
    }
}
