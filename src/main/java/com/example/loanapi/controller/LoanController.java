package com.example.loanapi.controller;

import com.example.loanapi.enums.InstallmentOptionEnum;
import com.example.loanapi.model.dto.PaymentResult;
import com.example.loanapi.entity.Loan;
import com.example.loanapi.entity.LoanInstallment;
import com.example.loanapi.model.request.*;
import com.example.loanapi.service.LoanInstallmentService;
import com.example.loanapi.service.LoanService;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/")
@Validated
public class LoanController {

    private final LoanService loanService;
    private final LoanInstallmentService installmentService;

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    public LoanController(LoanService loanService, LoanInstallmentService installmentService) {
        this.loanService = loanService;
        this.installmentService = installmentService;
    }

    //todo createLoan, listLoans, listInstallmentsForGivenLoan, payLoan

    // --- Loan Endpoints ---

    /**
     * Create a new loan
     * Accessible by both ADMIN and CUSTOMER roles
     */
    @PostMapping("/loans/createLoan")
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isCustomerOwner(#requestDTO.customerId, authentication)")
    public Loan createLoan(@RequestBody @Valid CreateLoanRequestDTO requestDTO) {

        if (!InstallmentOptionEnum.isValidInstallment(requestDTO.getNumberOfInstallments())) {
            throw new IllegalArgumentException("Number of installments must be 6,9,12 or 24");
        }

        return loanService.createLoan(requestDTO);
    }

    /**
     * Get loans for a specific customer
     * Accessible by both ADMIN and CUSTOMER roles
     */
    @PostMapping("/loans/getLoansByCustomer/")
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isCustomerOwner(#requestDTO.customerId, authentication)")
    public List<Loan> getLoansByCustomer(@RequestBody @Valid GetLoansByCustomerRequestDTO requestDTO) {
        return loanService.listLoans(requestDTO.getCustomerId(), requestDTO.getPaid());
    }

    // --- Installment Endpoints ---

    /**
     * Get all installments for a specific loan
     * Accessible by both ADMIN and CUSTOMER roles
     */
    @PostMapping("/installments/getInstallmentsByLoan/")
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isCustomerOwnerOfLoan(#requestDTO.loanId, authentication)")
    public List<LoanInstallment> getInstallmentsByLoan(@RequestBody @Valid GetInstallmentsByLoanRequestDTO requestDTO) {
        return installmentService.findByLoanId(requestDTO.getLoanId());
    }

    // --- Payment Endpoints ---

    /**
     * Pay loan installments
     * Accessible by both ADMIN and CUSTOMER roles
     */
    @PostMapping("/loans/payLoan/")
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isCustomerOwnerOfLoan(#requestDTO.loanId, authentication)")
    public PaymentResult payLoan(@RequestBody @Valid PayLoanRequestDTO requestDTO) {
        return loanService.payLoan(requestDTO.getLoanId(), requestDTO.getLoanAmount());
    }

}

