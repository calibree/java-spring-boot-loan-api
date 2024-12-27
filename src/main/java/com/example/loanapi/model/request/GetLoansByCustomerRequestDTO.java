package com.example.loanapi.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class GetLoansByCustomerRequestDTO {

    @NotNull(message = "Customer ID is required")
    @Min(value = 1, message = "Customer ID must be a positive number")
    private Long customerId;

    private Boolean isPaid;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
