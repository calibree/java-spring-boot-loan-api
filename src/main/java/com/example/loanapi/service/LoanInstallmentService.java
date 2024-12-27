package com.example.loanapi.service;

import com.example.loanapi.entity.LoanInstallment;
import com.example.loanapi.repository.LoanInstallmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanInstallmentService {
    private final LoanInstallmentRepository installmentRepository;

    public LoanInstallmentService(LoanInstallmentRepository installmentRepository) {
        this.installmentRepository = installmentRepository;
    }

    public List<LoanInstallment> findByLoanId(Long loanId) {
        return installmentRepository.findByLoanId(loanId);
    }
}
