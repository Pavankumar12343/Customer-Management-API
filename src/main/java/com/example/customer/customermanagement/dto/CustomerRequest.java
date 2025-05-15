package com.example.customer.customermanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CustomerRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private BigDecimal annualSpend;

    private LocalDateTime lastPurchaseDate;

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
