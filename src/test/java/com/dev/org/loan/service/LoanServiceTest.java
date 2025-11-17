package com.dev.org.loan.service;

import com.dev.org.loan.factory.LoanProcessorFactory;
import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.model.LoanResponse;
import com.dev.org.loan.model.LoanType;
import com.dev.org.loan.template.HomeLoanProcessor;
import com.dev.org.loan.template.LoanNullProcessor;
import com.dev.org.loan.util.MockDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LoanServiceTest {

    @Mock
    private LoanProcessorFactory loanProcessorFactory;

    @Mock
    private MockDataProvider mockDataProvider;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockDataProvider.generateApplicationId()).thenReturn("LN-2025-TEST");
    }

    @Test
    void shouldApproveHomeLoanSuccessfully() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("John Doe", LoanType.HOME, 250000, 400000, 95000, 15);
        when(loanProcessorFactory.getProcessor(LoanType.HOME)).thenReturn(new HomeLoanProcessor());

        // When
        LoanResponse response = loanService.processLoanApplication(request);

        // Then
        assertEquals("APPROVED", response.getStatus());
        assertEquals(225000, response.getApprovedAmount());
        assertEquals(6.5, response.getInterestRate());
        assertEquals("Loan approved based on eligibility rules", response.getMessage());
    }

    @Test
    void shouldDenyLoanWithInsufficientIncome() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("Jane Doe", LoanType.HOME, 250000, 400000, 25000, 15);
        when(loanProcessorFactory.getProcessor(LoanType.HOME)).thenReturn(new HomeLoanProcessor());

        // When
        LoanResponse response = loanService.processLoanApplication(request);

        // Then
        assertEquals("DENIED", response.getStatus());
        assertEquals("Initial validation failed.", response.getMessage());
    }

    @Test
    void shouldHandleInvalidLoanTypeGracefully() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("Sam Smith", null, 10000, 0, 50000, 5);
        when(loanProcessorFactory.getProcessor(null)).thenReturn(new LoanNullProcessor());

        // When
        LoanResponse response = loanService.processLoanApplication(request);

        // Then
        assertEquals("DENIED", response.getStatus());
        assertEquals("Invalid loan type specified.", response.getMessage());
    }
}
