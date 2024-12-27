package com.example.loanapi.model.request;

import com.example.loanapi.model.dto.PaymentResult;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public class PayLoanRequestDTO {

    @NotNull(message = "Loan ID is required")
    @Min(value = 1, message = "Loan ID must be a positive number")
    private Long loanId;

    @NotNull(message = "Loan amount is required")
    @Min(value = 1, message = "Loan amount must be greater than 0")
    @Schema(description = "Requested loan amount to be paid", example = "400.00")
    private BigDecimal loanAmount;

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }
}
