package com.dev.org.loan.strategy;

import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.model.LoanType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultRateStrategyTest {

    @Test
    void shouldReturnDefaultRate() {
        DefaultRateStrategy strategy = new DefaultRateStrategy();
        LoanApplicationRequest request = new LoanApplicationRequest("Test", LoanType.BUSINESS, 100000, 0, 200000, 5);
        assertEquals(8.0, strategy.calculate(request));
    }
}
