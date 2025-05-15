package com.example.customer.customermanagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.customermanagement.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
}
