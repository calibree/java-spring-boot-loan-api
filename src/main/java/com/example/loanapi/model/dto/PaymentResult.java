package com.example.loanapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class PaymentResult {
    private int installmentsPaid;
    private BigDecimal totalSpent;
    private boolean loanPaidOff;

    public PaymentResult() {
    }

    public PaymentResult(int installmentsPaid, BigDecimal totalSpent, boolean loanPaidOff) {
        this.installmentsPaid = installmentsPaid;
        this.totalSpent = totalSpent;
        this.loanPaidOff = loanPaidOff;
    }

    public int getInstallmentsPaid() {
        return installmentsPaid;
    }

    public void setInstallmentsPaid(int installmentsPaid) {
        this.installmentsPaid = installmentsPaid;
    }

    public BigDecimal getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(BigDecimal totalSpent) {
        this.totalSpent = totalSpent;
    }

    public boolean isLoanPaidOff() {
        return loanPaidOff;
    }

    public void setLoanPaidOff(boolean loanPaidOff) {
        this.loanPaidOff = loanPaidOff;
    }
}
