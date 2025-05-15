package com.example.customer.customermanagement.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerResponse extends CustomerRequest {

    private UUID id;
    private String tier;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}

    
}
