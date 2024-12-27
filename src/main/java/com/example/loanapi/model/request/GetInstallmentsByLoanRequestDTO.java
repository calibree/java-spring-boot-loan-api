package com.example.loanapi.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GetInstallmentsByLoanRequestDTO {

    @NotNull(message = "Loan ID is required")
    @Min(value = 1, message = "Loan ID must be a positive number")
    private Long loanId;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
}
