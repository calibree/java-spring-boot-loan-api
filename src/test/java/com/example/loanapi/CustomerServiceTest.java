package com.example.loanapi;

import com.example.loanapi.entity.Customer;
import com.example.loanapi.repository.CustomerRepository;
import com.example.loanapi.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CustomerServiceTest {

    private CustomerService customerService;

    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    public void testFindById_Success() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerService.findById(customerId);
        assertTrue(foundCustomer.isPresent());
        assertEquals(customerId, foundCustomer.get().getId());
    }

    @Test
    public void testFindById_NotFound() {
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        Optional<Customer> foundCustomer = customerService.findById(customerId);
        assertFalse(foundCustomer.isPresent());
    }

    @Test
    public void testFindAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(1L);
        customer1.setName("John Doe");

        Customer customer2 = new Customer();
        customer2.setId(2L);
        customer2.setName("Jane Doe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.findAllCustomers();
        assertNotNull(customers);
        assertEquals(2, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
        assertEquals("Jane Doe", customers.get(1).getName());
    }

    @Test
    public void testFindAllCustomers_EmptyList() {
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        List<Customer> customers = customerService.findAllCustomers();
        assertNotNull(customers);
        assertTrue(customers.isEmpty());
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);
        assertNotNull(createdCustomer);
        assertEquals("John Doe", createdCustomer.getName());
    }
}


