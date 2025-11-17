package com.dev.org.loan.strategy;

import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.model.LoanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LtvBasedInterestStrategyTest {

    private LtvBasedInterestStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new LtvBasedInterestStrategy();
    }

    @Test
    void shouldReturnLowerRateForLowLtv() {
        LoanApplicationRequest request = new LoanApplicationRequest("Test", LoanType.HOME, 200000, 400000, 100000, 30);
        assertEquals(5.5, strategy.calculate(request));
    }

    @Test
    void shouldReturnMediumRateForMediumLtv() {
        LoanApplicationRequest request = new LoanApplicationRequest("Test", LoanType.HOME, 300000, 400000, 100000, 30);
        assertEquals(6.5, strategy.calculate(request));
    }

    @Test
    void shouldReturnHigherRateForHighLtv() {
        LoanApplicationRequest request = new LoanApplicationRequest("Test", LoanType.HOME, 350000, 400000, 100000, 30);
        assertEquals(7.5, strategy.calculate(request));
    }
}
