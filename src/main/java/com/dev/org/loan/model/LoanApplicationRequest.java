package com.dev.org.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequest {
    private String applicantName;
    private LoanType loanType;
    private double requestedAmount;
    private double propertyValue;
    private double income;
    private int loanTermYears;
}
