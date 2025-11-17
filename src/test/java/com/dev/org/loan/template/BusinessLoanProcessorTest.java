package com.dev.org.loan.template;

import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.model.LoanStatus;
import com.dev.org.loan.model.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BusinessLoanProcessorTest {

    private BusinessLoanProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new BusinessLoanProcessor();
    }

    @Test
    void shouldApproveBusinessLoanWhenValidationPasses() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("John Doe", LoanType.BUSINESS, 100000, 0, 150000, 10);
        LoanStatus.Submitted initialState = new LoanStatus.Submitted(request);

        // When
        LoanStatus finalState = processor.process(initialState);

        // Then
        assertInstanceOf(LoanStatus.Approved.class, finalState);
        LoanStatus.Approved approvedState = (LoanStatus.Approved) finalState;
        assertEquals(70000, approvedState.approvedAmount()); // 70% of 100000
        assertEquals(8.0, approvedState.interestRate()); // Default rate
    }

    @Test
    void shouldDenyBusinessLoanWhenValidationFails() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("Jane Doe", LoanType.BUSINESS, 100000, 0, 20000, 10); // Low income
        LoanStatus.Submitted initialState = new LoanStatus.Submitted(request);

        // When
        LoanStatus finalState = processor.process(initialState);

        // Then
        assertInstanceOf(LoanStatus.Denied.class, finalState);
        assertEquals("Validation failed for business loan.", ((LoanStatus.Denied) finalState).reason());
    }
}
