package com.example.customer.customermanagement.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private BigDecimal annualSpend;

    private LocalDateTime lastPurchaseDate;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getAnnualSpend() {
		return annualSpend;
	}

	public void setAnnualSpend(BigDecimal annualSpend) {
		this.annualSpend = annualSpend;
	}

	public LocalDateTime getLastPurchaseDate() {
		return lastPurchaseDate;
	}

	public void setLastPurchaseDate(LocalDateTime lastPurchaseDate) {
		this.lastPurchaseDate = lastPurchaseDate;
	}

    
}

