package com.example.loanapi.service;

import com.example.loanapi.model.dto.PaymentResult;
import com.example.loanapi.entity.Customer;
import com.example.loanapi.entity.Loan;
import com.example.loanapi.entity.LoanInstallment;
import com.example.loanapi.model.request.CreateLoanRequestDTO;
import com.example.loanapi.repository.CustomerRepository;
import com.example.loanapi.repository.LoanInstallmentRepository;
import com.example.loanapi.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final LoanInstallmentRepository installmentRepository;

    public LoanService(LoanRepository loanRepository, CustomerRepository customerRepository, LoanInstallmentRepository installmentRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.installmentRepository = installmentRepository;
    }

    public List<Loan> findByCustomerId(Long customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    public Loan createLoan(CreateLoanRequestDTO requestDTO) {
        Customer customer = customerRepository.findById(requestDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // Validate credit limit
        BigDecimal totalCreditUsage = customer.getUsedCreditLimit().add(requestDTO.getLoanAmount());
        if (totalCreditUsage.compareTo(customer.getCreditLimit()) > 0 ) {
            throw new IllegalArgumentException("Insufficient credit limit");
        }

        // Calculate total loan amount
        BigDecimal totalLoanAmount = requestDTO.getLoanAmount().multiply(requestDTO.getInterestRate().add(BigDecimal.valueOf(1)));
        BigDecimal installmentAmount = divideWithRounding(totalLoanAmount, BigDecimal.valueOf(requestDTO.getNumberOfInstallments()), 2);

        // Create loan
        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanAmount(requestDTO.getLoanAmount());
        loan.setNumberOfInstallments(requestDTO.getNumberOfInstallments());
        loan.setCreateDate(LocalDate.now());
        loan.setIsPaid(false);
        loan = loanRepository.save(loan);

        // Generate installments
        List<LoanInstallment> installments = new ArrayList<>();
        LocalDate dueDate = LocalDate.now().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        for (int i = 0; i < requestDTO.getNumberOfInstallments(); i++) {
            LoanInstallment installment = new LoanInstallment();
            installment.setLoan(loan);
            BigDecimal roundedInstallmentAmount = installmentAmount.setScale(2, RoundingMode.HALF_UP); // Round to 2 decimal places
            //BigDecimal lastInstallmentAmount = i == requestDTO.getNumberOfInstallments() - 1
            //        ? roundedInstallmentAmount : installmentAmount;
            installment.setAmount(roundedInstallmentAmount);
            installment.setPaidAmount(BigDecimal.valueOf(0));
            installment.setDueDate(dueDate);
            installment.setIsPaid(false);
            installments.add(installment);

            dueDate = dueDate.plusMonths(1);
        }
        installmentRepository.saveAll(installments);

        // Update customer credit usage
        customer.setUsedCreditLimit(totalCreditUsage);
        customerRepository.save(customer);

        return loan;
    }

    public PaymentResult payLoan(Long loanId, BigDecimal amount) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        List<LoanInstallment> installments = installmentRepository.findByLoanId(loanId);
        installments.sort((a, b) -> a.getDueDate().compareTo(b.getDueDate()));

        BigDecimal remainingAmount = amount;
        int installmentsPaid = 0;
        BigDecimal totalSpent = BigDecimal.valueOf(0);

        for (LoanInstallment installment : installments) {

            if (installment.getIsPaid()) continue;

            LocalDate today = LocalDate.now();
            if(today.plusMonths(3).isBefore(installment.getDueDate())){
                break;
            }

            BigDecimal adjustment = BigDecimal.ZERO;
            BigDecimal dailyRate = new BigDecimal("0.001"); // 0.001 as BigDecimal

            if (today.isBefore(installment.getDueDate())) {
                long daysEarly = installment.getDueDate().toEpochDay() - today.toEpochDay();
                adjustment = installment.getAmount()
                        .multiply(dailyRate)
                        .multiply(BigDecimal.valueOf(daysEarly))
                        .negate(); // Negative adjustment for early payment
            } else if (today.isAfter(installment.getDueDate())) { // Apply penalty for late payment
                long daysLate = today.toEpochDay() - installment.getDueDate().toEpochDay();
                adjustment = installment.getAmount()
                        .multiply(dailyRate)
                        .multiply(BigDecimal.valueOf(daysLate)); // Positive adjustment for late payment
            }

            BigDecimal adjustedAmount = installment.getAmount().add(adjustment).setScale(2, RoundingMode.HALF_UP);

            // Pay the installment if the remaining amount is sufficient
            if (remainingAmount.compareTo(adjustedAmount) >= 0) {
                installment.setPaidAmount(adjustedAmount);
                installment.setPaymentDate(today);
                installment.setIsPaid(true);

                remainingAmount = remainingAmount.subtract(adjustedAmount); // Subtract adjustedAmount from remainingAmount
                totalSpent = totalSpent.add(adjustedAmount); // Add adjustedAmount to totalSpent
                installmentsPaid++; // Increment the number of installments paid
            } else {
                break; // Stop processing if remaining amount is insufficient
            }

            installmentRepository.save(installment);
        }

        // Update loan status if all installments are paid
        if (installments.stream().allMatch(LoanInstallment::getIsPaid)) {
            loan.setIsPaid(true);
            loanRepository.save(loan);
        }

        return new PaymentResult(installmentsPaid, totalSpent, loan.getIsPaid());
    }

    public List<Loan> listLoans(Long customerId, Boolean isPaid) {
        if (isPaid != null) {
            return loanRepository.findByCustomerIdAndIsPaid(customerId, isPaid);
        } else {
            return loanRepository.findByCustomerId(customerId);
        }
    }

    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    private BigDecimal divideWithRounding(BigDecimal numerator, BigDecimal denominator, int scale) {
        return numerator.divide(denominator, scale, RoundingMode.HALF_UP);
    }
}
