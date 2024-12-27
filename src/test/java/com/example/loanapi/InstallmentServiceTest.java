package com.example.loanapi;

import com.example.loanapi.entity.Loan;
import com.example.loanapi.entity.LoanInstallment;
import com.example.loanapi.repository.LoanInstallmentRepository;
import com.example.loanapi.service.LoanInstallmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class InstallmentServiceTest {

    private LoanInstallmentService loanInstallmentService;
    private LoanInstallmentRepository installmentRepository;

    @BeforeEach
    public void setUp() {
        installmentRepository = Mockito.mock(LoanInstallmentRepository.class);
        loanInstallmentService = new LoanInstallmentService(installmentRepository);
    }

    @Test
    public void testFindByLoanId_Success() {
        Long loanId = 1L;
        Loan loan1 = new Loan();
        loan1.setId(loanId);
        LoanInstallment installment1 = new LoanInstallment();
        installment1.setId(1L);
        installment1.setLoan(loan1);

        LoanInstallment installment2 = new LoanInstallment();
        installment2.setId(2L);
        installment2.setLoan(loan1);

        when(installmentRepository.findByLoanId(loanId)).thenReturn(Arrays.asList(installment1, installment2));

        List<LoanInstallment> installments = loanInstallmentService.findByLoanId(loanId);
        assertNotNull(installments);
        assertEquals(2, installments.size());
        assertEquals(loanId, installments.get(0).getLoan().getId());
        assertEquals(loanId, installments.get(1).getLoan().getId());
    }

    @Test
    public void testFindByLoanId_NoInstallments() {
        Long loanId = 1L;

        when(installmentRepository.findByLoanId(loanId)).thenReturn(Collections.emptyList());

        List<LoanInstallment> installments = loanInstallmentService.findByLoanId(loanId);
        assertNotNull(installments);
        assertTrue(installments.isEmpty());
    }
}
