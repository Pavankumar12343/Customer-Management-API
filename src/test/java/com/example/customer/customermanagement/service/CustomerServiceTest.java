package com.example.customer.customermanagement.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.customer.customermanagement.dto.CustomerRequest;
import com.example.customer.customermanagement.dto.CustomerResponse;
import com.example.customer.customermanagement.model.Customer;
import com.example.customer.customermanagement.repository.CustomerRepository;
import com.example.customer.customermanagement.service.CustomerService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;

    private final UUID id = UUID.randomUUID();
    private Customer customer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(id);
        customer.setName("Test User");
        customer.setEmail("test@example.com");
        customer.setAnnualSpend(new BigDecimal("5000"));
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(3));
    }

    @Test
    void testCreateCustomer() {
        CustomerRequest request = new CustomerRequest();
        request.setName("New User");
        request.setEmail("new@example.com");
        when(repository.save(any(Customer.class))).thenAnswer(i -> {
            Customer c = i.getArgument(0);
            c.setId(UUID.randomUUID());
            return c;
        });

        CustomerResponse response = service.createCustomer(request);
        assertNotNull(response.getId());
        assertEquals("New User", response.getName());
    }

    @Test
    void testGetCustomerById() {
        when(repository.findById(id)).thenReturn(Optional.of(customer));
        CustomerResponse response = service.getCustomerById(id);
        assertEquals("Test User", response.getName());
    }

    @Test
    void testUpdateCustomer() {
        CustomerRequest update = new CustomerRequest();
        update.setName("Updated");
        update.setEmail("updated@example.com");
        update.setAnnualSpend(new BigDecimal("12000"));
        update.setLastPurchaseDate(LocalDateTime.now());

        when(repository.findById(id)).thenReturn(Optional.of(customer));
        when(repository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponse response = service.updateCustomer(id, update);
        assertEquals("Updated", response.getName());
        assertEquals("Platinum", response.getTier());
    }

    @Test
    void testDeleteCustomerNotFound() {
        when(repository.existsById(id)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteCustomer(id));
    }

    @Test
    void testTierCalculation_Silver() {
        customer.setAnnualSpend(new BigDecimal("999"));
        customer.setLastPurchaseDate(LocalDateTime.now().minusYears(2));
        when(repository.findById(id)).thenReturn(Optional.of(customer));
        CustomerResponse response = service.getCustomerById(id);
        assertEquals("Silver", response.getTier());
    }

    @Test
    void testTierCalculation_Gold() {
        customer.setAnnualSpend(new BigDecimal("3000"));
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(10));
        when(repository.findById(id)).thenReturn(Optional.of(customer));
        CustomerResponse response = service.getCustomerById(id);
        assertEquals("Gold", response.getTier());
    }

    @Test
    void testTierCalculation_Platinum() {
        customer.setAnnualSpend(new BigDecimal("10000"));
        customer.setLastPurchaseDate(LocalDateTime.now().minusMonths(3));
        when(repository.findById(id)).thenReturn(Optional.of(customer));
        CustomerResponse response = service.getCustomerById(id);
        assertEquals("Platinum", response.getTier());
    }
}
