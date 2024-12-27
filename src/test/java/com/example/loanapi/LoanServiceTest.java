package com.example.loanapi;

import com.example.loanapi.entity.Customer;
import com.example.loanapi.entity.Loan;
import com.example.loanapi.entity.LoanInstallment;
import com.example.loanapi.model.dto.PaymentResult;
import com.example.loanapi.model.request.CreateLoanRequestDTO;
import com.example.loanapi.repository.CustomerRepository;
import com.example.loanapi.repository.LoanInstallmentRepository;
import com.example.loanapi.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.loanapi.service.LoanService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class LoanServiceTest {

    private LoanService loanService;
    private LoanRepository loanRepository;
    private CustomerRepository customerRepository;
    private LoanInstallmentRepository installmentRepository;

    @BeforeEach
    public void setUp() {
        loanRepository = Mockito.mock(LoanRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        installmentRepository = Mockito.mock(LoanInstallmentRepository.class);
        loanService = new LoanService(loanRepository, customerRepository, installmentRepository);
    }

    @Test
    public void testFindByCustomerId() {
        Long customerId = 1L;
        Loan loan = new Loan();
        loan.setCustomer(new Customer());
        Mockito.when(loanRepository.findByCustomerId(customerId)).thenReturn(Collections.singletonList(loan));

        List<Loan> loans = loanService.findByCustomerId(customerId);
        assertNotNull(loans);
        assertEquals(1, loans.size());
    }

    @Test
    public void testCreateLoan() {
        CreateLoanRequestDTO requestDTO = new CreateLoanRequestDTO();
        requestDTO.setCustomerId(1L);
        requestDTO.setLoanAmount(BigDecimal.valueOf(1000));
        requestDTO.setInterestRate(BigDecimal.valueOf(0.05));
        requestDTO.setNumberOfInstallments(12);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCreditLimit(BigDecimal.valueOf(2000));
        customer.setUsedCreditLimit(BigDecimal.ZERO);

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        Mockito.when(loanRepository.save(any(Loan.class))).thenReturn(new Loan());
        Mockito.when(installmentRepository.saveAll(any())).thenReturn(Collections.emptyList());

        Loan loan = loanService.createLoan(requestDTO);
        assertNotNull(loan);
    }

    @Test
    public void testCreateLoanInsufficientCreditLimit() {
        CreateLoanRequestDTO requestDTO = new CreateLoanRequestDTO();
        requestDTO.setCustomerId(1L);
        requestDTO.setLoanAmount(BigDecimal.valueOf(3000)); // Exceeds credit limit
        requestDTO.setInterestRate(BigDecimal.valueOf(0.05));
        requestDTO.setNumberOfInstallments(12);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setCreditLimit(BigDecimal.valueOf(2000));
        customer.setUsedCreditLimit(BigDecimal.ZERO);

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(requestDTO);
        });

        assertEquals("Insufficient credit limit", exception.getMessage());
    }

    @Test
    public void testPayLoan() {
        Long loanId = 1L;
        Loan loan = new Loan();
        loan.setId(loanId);
        loan.setIsPaid(false);

        LoanInstallment installment = new LoanInstallment();
        installment.setAmount(BigDecimal.valueOf(100));
        installment.setIsPaid(false);
        installment.setLoan(loan);
        installment.setDueDate(LocalDate.now().plusMonths(1));

        Mockito.when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        Mockito.when(installmentRepository.findByLoanId(loanId)).thenReturn(Collections.singletonList(installment));

        PaymentResult paymentResult = loanService.payLoan(loanId, BigDecimal.valueOf(100));

        assertNotNull(paymentResult);
        assertEquals(1, paymentResult.getInstallmentsPaid());
        assertTrue(loan.getIsPaid());
    }

    @Test
    public void testPayLoanNotFound() {
        Long loanId = 1L;

        Mockito.when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.payLoan(loanId, BigDecimal.valueOf(100));
        });

        assertEquals("Loan not found", exception.getMessage());
    }

    @Test
    public void testListLoans() {
        Long customerId = 1L;
        Loan loan = new Loan();
        loan.setCustomer(new Customer());

        Mockito.when(loanRepository.findByCustomerIdAndIsPaid(anyLong(), any())).thenReturn(Collections.singletonList(loan));

        List<Loan> loans = loanService.listLoans(customerId, true);
        assertNotNull(loans);
        assertEquals(1, loans.size());
    }

    @Test
    public void testFindById() {
        Long loanId = 1L;
        Loan loan = new Loan();
        loan.setId(loanId);

        Mockito.when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        Optional<Loan> foundLoan = loanService.findById(loanId);
        assertTrue(foundLoan.isPresent());
        assertEquals(loanId, foundLoan.get().getId());
    }

}

