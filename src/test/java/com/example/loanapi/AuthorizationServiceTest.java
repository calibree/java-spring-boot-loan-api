package com.example.loanapi;

import com.example.loanapi.entity.Customer;
import com.example.loanapi.entity.Loan;
import com.example.loanapi.service.AuthorizationService;
import com.example.loanapi.service.CustomerService;
import com.example.loanapi.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;
    private CustomerService customerService;
    private LoanService loanService;
    private Authentication authentication;

    @BeforeEach
    public void setUp() {
        customerService = Mockito.mock(CustomerService.class);
        loanService = Mockito.mock(LoanService.class);
        authorizationService = new AuthorizationService(customerService, loanService);

        // Mocking Authentication
        authentication = Mockito.mock(Authentication.class);
    }

    @Test
    public void testIsCustomerOwner_Success() {
        Long customerId = 1L;
        String username = "testUser";

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName(username);

        when(authentication.getName()).thenReturn(username);
        when(customerService.findById(customerId)).thenReturn(Optional.of(customer));

        boolean result = authorizationService.isCustomerOwner(customerId, authentication);
        assertTrue(result);
    }

    @Test
    public void testIsCustomerOwner_CustomerNotFound() {
        Long customerId = 1L;
        String username = "testUser";

        when(authentication.getName()).thenReturn(username);
        when(customerService.findById(customerId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorizationService.isCustomerOwner(customerId, authentication);
        });

        assertEquals("Customer not present", exception.getMessage());
    }

    @Test
    public void testIsCustomerOwner_AccessDenied() {
        Long customerId = 1L;
        String username = "testUser";

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("otherUser"); // Different name

        when(authentication.getName()).thenReturn(username);
        when(customerService.findById(customerId)).thenReturn(Optional.of(customer));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            authorizationService.isCustomerOwner(customerId, authentication);
        });

        assertEquals("Access denied: You can only access your own data!", exception.getMessage());
    }

    @Test
    public void testIsCustomerOwnerOfLoan_Success() {
        Long loanId = 1L;
        String username = "testUser";

        Loan loan = new Loan();
        loan.setId(loanId);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName(username);
        loan.setCustomer(customer);

        when(authentication.getName()).thenReturn(username);
        when(loanService.findById(loanId)).thenReturn(Optional.of(loan));
        when(customerService.findById(2L)).thenReturn(Optional.of(customer));

        boolean result = authorizationService.isCustomerOwnerOfLoan(loanId, authentication);
        assertTrue(result);
    }

    @Test
    public void testIsCustomerOwnerOfLoan_LoanNotFound() {
        Long loanId = 1L;
        String username = "testUser";

        when(authentication.getName()).thenReturn(username);
        when(loanService.findById(loanId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorizationService.isCustomerOwnerOfLoan(loanId, authentication);
        });

        assertEquals("Loan not present", exception.getMessage());
    }

    @Test
    public void testIsCustomerOwnerOfLoan_CustomerNotFound() {
        Long loanId = 1L;
        String username = "testUser";

        Loan loan = new Loan();
        loan.setId(loanId);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("otherUser"); // Different name
        loan.setCustomer(customer);

        when(authentication.getName()).thenReturn(username);
        when(loanService.findById(loanId)).thenReturn(Optional.of(loan));
        when(customerService.findById(2L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            authorizationService.isCustomerOwnerOfLoan(loanId, authentication);
        });

        assertEquals("Customer not present", exception.getMessage());
    }

    @Test
    public void testIsCustomerOwnerOfLoan_AccessDenied() {
        Long loanId = 1L;
        String username = "testUser";

        Loan loan = new Loan();
        loan.setId(loanId);
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("otherUser"); // Different name
        loan.setCustomer(customer);

        when(authentication.getName()).thenReturn(username);
        when(loanService.findById(loanId)).thenReturn(Optional.of(loan));
        when(customerService.findById(2L)).thenReturn(Optional.of(customer));

        AccessDeniedException exception = assertThrows(AccessDeniedException.class, () -> {
            authorizationService.isCustomerOwnerOfLoan(loanId, authentication);
        });

        assertEquals("Access denied: You can only access your own data!", exception.getMessage());
    }
}
