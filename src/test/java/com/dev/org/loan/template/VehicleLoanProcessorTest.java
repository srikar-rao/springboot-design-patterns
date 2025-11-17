package com.dev.org.loan.template;

import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.model.LoanStatus;
import com.dev.org.loan.model.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class VehicleLoanProcessorTest {

    private VehicleLoanProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new VehicleLoanProcessor();
    }

    @Test
    void shouldApproveVehicleLoanWhenValidationPasses() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("John Doe", LoanType.VEHICLE, 20000, 0, 50000, 5);
        LoanStatus.Submitted initialState = new LoanStatus.Submitted(request);

        // When
        LoanStatus finalState = processor.process(initialState);

        // Then
        assertInstanceOf(LoanStatus.Approved.class, finalState);
        LoanStatus.Approved approvedState = (LoanStatus.Approved) finalState;
        assertEquals(16000, approvedState.approvedAmount()); // 80% of 20000
        assertEquals(8.0, approvedState.interestRate()); // Default rate
    }

    @Test
    void shouldDenyVehicleLoanWhenValidationFails() {
        // Given
        LoanApplicationRequest request = new LoanApplicationRequest("Jane Doe", LoanType.VEHICLE, 20000, 0, 20000, 5); // Low income
        LoanStatus.Submitted initialState = new LoanStatus.Submitted(request);

        // When
        LoanStatus finalState = processor.process(initialState);

        // Then
        assertInstanceOf(LoanStatus.Denied.class, finalState);
        assertEquals("Validation failed for vehicle loan.", ((LoanStatus.Denied) finalState).reason());
    }
}
