package com.dev.org.loan.model;

/**
 * Represents the state of a loan application using a sealed interface.
 * This is a modern approach to implementing the State pattern in Java.
 */
public sealed interface LoanStatus {
    record Submitted(LoanApplicationRequest request) implements LoanStatus {}
    record Reviewed(LoanApplicationRequest request, String notes) implements LoanStatus {}
    record Approved(double approvedAmount, double interestRate) implements LoanStatus {}
    record Denied(String reason) implements LoanStatus {}
}
