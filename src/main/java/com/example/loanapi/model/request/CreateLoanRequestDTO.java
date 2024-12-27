package com.example.loanapi.model.request;

import com.example.loanapi.enums.InstallmentOptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreateLoanRequestDTO {

    @NotNull(message = "Customer ID is required")
    @Min(value = 1, message = "Customer ID must be a positive number")
    private Long customerId;

    @NotNull(message = "Loan amount is required")
    @Min(value = 1, message = "Loan amount must be greater than 0")
    @Schema(description = "Requested loan amount", example = "1200.00")
    private BigDecimal loanAmount;

    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.1", inclusive = true, message = "Value must be greater than or equal to 0.1")
    @DecimalMax(value = "0.5", inclusive = true, message = "Value must be greater than or equal to 0.5")
    private BigDecimal interestRate;

    @NotNull(message = "Number of installments is required")
    @Min(value = 6)
    private Integer numberOfInstallments;

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        if (!InstallmentOptionEnum.isValidInstallment(numberOfInstallments)) {
            throw new IllegalArgumentException("Number of installments must be 6,9,12 or 24");
        }
        this.numberOfInstallments = numberOfInstallments;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}

