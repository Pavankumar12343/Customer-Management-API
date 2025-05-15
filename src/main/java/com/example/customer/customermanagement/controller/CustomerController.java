package com.example.customer.customermanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.customer.customermanagement.dto.CustomerRequest;
import com.example.customer.customermanagement.dto.CustomerResponse;
import com.example.customer.customermanagement.service.CustomerService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.status(201).body(service.createCustomer(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> getCustomerByNameOrEmail(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> email) {
        return ResponseEntity.ok(service.getCustomerByNameOrEmail(name, email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(service.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
