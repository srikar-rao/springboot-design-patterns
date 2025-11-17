package com.dev.org.loan.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Utility class to provide mock data for the loan application process.
 */
@Component
public class MockDataProvider {

    public String generateApplicationId() {
        return "LN-2025-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }
}
