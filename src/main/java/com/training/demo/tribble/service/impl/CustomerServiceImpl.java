package com.training.demo.tribble.service.impl;

import com.training.demo.tribble.domain.Customer;
import com.training.demo.tribble.dto.CustomerDto;
import com.training.demo.tribble.repository.CustomerRepository;
import com.training.demo.tribble.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        logger.info("Creating customer: {}", customerDto.email());
        if (customerRepository.existsByEmail(customerDto.email())) {
            throw new DataIntegrityViolationException("Customer with email already exists");
        }
        Customer customer = new Customer(
            customerDto.firstName(),
            customerDto.lastName(),
            customerDto.email(),
            customerDto.phone(),
            customerDto.address()
        );
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> listCustomers() {
        logger.debug("Listing all customers");
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(UUID id) {
        return customerRepository.findById(id).orElse(null);
    }
}

