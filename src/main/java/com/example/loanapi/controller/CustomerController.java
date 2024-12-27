package com.example.loanapi.controller;

import com.example.loanapi.entity.Customer;
import com.example.loanapi.service.CustomerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // --- Customer Endpoints ---

    /**
     * Get current user's roles for authentication test
     * Accessible by ALL
     */

    @GetMapping("/role")
    public String getRole(Authentication authentication) {
        return authentication.getAuthorities().toString();
    }

    /**
     * Get all customers
     * Accessible by ADMIN
     */
    @GetMapping("/admin/getAllCustomers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    /**
     * Get customer by ID
     * Accessible by ADMIN
     * Accessible by customer if its customers own data
     */
    @GetMapping("/customers/getCustomerById/{id}")
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isCustomerOwner(#id, authentication)")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }
}
