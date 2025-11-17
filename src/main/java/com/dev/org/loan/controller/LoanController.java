package com.dev.org.loan.controller;

import com.dev.org.loan.model.LoanApplicationRequest;
import com.dev.org.loan.model.LoanResponse;
import com.dev.org.loan.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanResponse> applyForLoan(@RequestBody LoanApplicationRequest request) {
        LoanResponse response = loanService.processLoanApplication(request);
        return ResponseEntity.ok(response);
    }
}
