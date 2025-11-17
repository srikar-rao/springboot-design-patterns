package com.dev.org.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanResponse {
    private String applicationId;
    private String status;
    private double approvedAmount;
    private double interestRate;
    private String message;
}
