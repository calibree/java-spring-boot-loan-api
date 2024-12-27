package com.example.loanapi.repository;

import com.example.loanapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

//Spring Data JPA repositories for database operations

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
