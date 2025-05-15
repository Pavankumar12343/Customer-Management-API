package com.example.customer.customermanagement.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.customer.customermanagement.dto.CustomerRequest;
import com.example.customer.customermanagement.dto.CustomerResponse;
import com.example.customer.customermanagement.model.Customer;
import com.example.customer.customermanagement.repository.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = new Customer();
        updateFields(customer, request);
        Customer saved = repository.save(customer);
        return toResponse(saved);
    }

    public CustomerResponse getCustomerById(UUID id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        return toResponse(customer);
    }

    public CustomerResponse getCustomerByNameOrEmail(Optional<String> name, Optional<String> email) {
        Optional<Customer> customerOpt = name
            .flatMap(repository::findByName)
            .or(() -> email.flatMap(repository::findByEmail));

        Customer customer = customerOpt.orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        return toResponse(customer);
    }
    
    public CustomerResponse updateCustomer(UUID id, CustomerRequest request) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        updateFields(customer, request);
        Customer updated = repository.save(customer);
        return toResponse(updated);
    }

    public void deleteCustomer(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found");
        }
        repository.deleteById(id);
    }

    private void updateFields(Customer customer, CustomerRequest request) {
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setAnnualSpend(request.getAnnualSpend());
        customer.setLastPurchaseDate(request.getLastPurchaseDate());
    }

    private CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setAnnualSpend(customer.getAnnualSpend());
        response.setLastPurchaseDate(customer.getLastPurchaseDate());
        response.setTier(calculateTier(customer.getAnnualSpend(), customer.getLastPurchaseDate()));
        return response;
    }

    private String calculateTier(BigDecimal spend, LocalDateTime lastPurchase) {
        if (spend == null) return "Silver";
        LocalDateTime now = LocalDateTime.now();

        if (spend.compareTo(new BigDecimal("10000")) >= 0 &&
            lastPurchase != null &&
            lastPurchase.isAfter(now.minusMonths(6))) {
            return "Platinum";
        } else if (spend.compareTo(new BigDecimal("1000")) >= 0 &&
                   lastPurchase != null &&
                   lastPurchase.isAfter(now.minusMonths(12))) {
            return "Gold";
        } else {
            return "Silver";
        }
    }
}
