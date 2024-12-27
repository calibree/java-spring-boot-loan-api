package com.example.loanapi.service;

import com.example.loanapi.entity.Customer;
import com.example.loanapi.entity.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("authorizationService")
public class AuthorizationService {

    private final CustomerService customerService;
    private final LoanService loanService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    public AuthorizationService(CustomerService customerService, LoanService loanService) {
        this.customerService = customerService;
        this.loanService = loanService;
    }

    public boolean isCustomerOwner(Long customerId, Authentication authentication) {
        String currentUsername = authentication.getName();
        Customer customerInfo = customerService.findById(customerId).orElseThrow(() -> new IllegalArgumentException("Customer not present"));
        return isNameCheckSuccessful(currentUsername,customerInfo.getName());
    }

    public boolean isCustomerOwnerOfLoan(Long loanId, Authentication authentication) {
        String currentUsername = authentication.getName();
        Loan loanInfo = loanService.findById(loanId).orElseThrow(() -> new IllegalArgumentException("Loan not present"));
        Customer customer = customerService.findById((loanInfo.getCustomer().getId())).orElseThrow(() -> new IllegalArgumentException("Customer not present"));
        return isNameCheckSuccessful(currentUsername,customer.getName());
    }

    private boolean isNameCheckSuccessful(String authName, String nameFromDatabase){
        if(nameFromDatabase != null && nameFromDatabase.equalsIgnoreCase(authName)){
            logger.info("Authorization successful");
            return true;
        } else {
            throw new AccessDeniedException("Access denied: You can only access your own data!");
        }
    }
}
