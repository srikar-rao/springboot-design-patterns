package com.dev.org.loan.template;

import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.model.LoanStatus;
import com.dev.org.loan.model.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class HomeLoanProcessorTest {

    private HomeLoanProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new HomeLoanProcessor();
    }

    @Test
    void shouldApproveLoanWhenValidationPasses() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("John Doe", LoanType.HOME, 250000, 400000, 95000, 15);
        LoanStatus.Submitted initialState = new LoanStatus.Submitted(request);

        // When
        LoanStatus finalState = processor.process(initialState);

        // Then
        assertInstanceOf(LoanStatus.Approved.class, finalState);
        LoanStatus.Approved approvedState = (LoanStatus.Approved) finalState;
        assertEquals(225000, approvedState.approvedAmount());
        assertEquals(6.5, approvedState.interestRate());
    }

    @Test
    void shouldDenyLoanWhenValidationFails() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("Jane Doe", LoanType.HOME, 250000, 400000, 25000, 15); // Low income
        LoanStatus.Submitted initialState = new LoanStatus.Submitted(request);

        // When
        LoanStatus finalState = processor.process(initialState);

        // Then
        assertInstanceOf(LoanStatus.Denied.class, finalState);
        LoanStatus.Denied deniedState = (LoanStatus.Denied) finalState;
        assertEquals("Initial validation failed.", deniedState.reason());
    }
}
