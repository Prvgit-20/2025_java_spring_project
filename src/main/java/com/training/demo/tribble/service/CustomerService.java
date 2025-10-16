package com.training.demo.tribble.service;

import com.training.demo.tribble.dto.CustomerDto;
import com.training.demo.tribble.domain.Customer;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto);
    List<Customer> listCustomers();
    Customer getCustomer(UUID id);
}

