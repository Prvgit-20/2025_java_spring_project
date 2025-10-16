package com.training.demo.tribble.controller;

import com.training.demo.tribble.dto.CustomerDto;
import com.training.demo.tribble.domain.Customer;
import com.training.demo.tribble.service.CustomerService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
        try {
            Customer customer = customerService.createCustomer(customerDto);
            return ResponseEntity.ok(customer);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listCustomers() {
        return ResponseEntity.ok(customerService.listCustomers());
    }
}
