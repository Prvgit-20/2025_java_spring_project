package com.training.demo.tribble.service.impl;

import com.training.demo.tribble.domain.Customer;
import com.training.demo.tribble.domain.Mobile;
import com.training.demo.tribble.domain.Purchase;
import com.training.demo.tribble.dto.PurchaseRequestDto;
import com.training.demo.tribble.repository.CustomerRepository;
import com.training.demo.tribble.repository.MobileRepository;
import com.training.demo.tribble.repository.PurchaseRepository;
import com.training.demo.tribble.service.PurchaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final MobileRepository mobileRepository;
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, MobileRepository mobileRepository, CustomerRepository customerRepository) {
        this.purchaseRepository = purchaseRepository;
        this.mobileRepository = mobileRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Purchase createPurchase(PurchaseRequestDto dto) {
        logger.info("Creating purchase for mobile {} and customer {}", dto.mobile().imei(), dto.customer().email());
        Mobile mobile = mobileRepository.findAll().stream()
            .filter(m -> m.getImei().equals(dto.mobile().imei()))
            .findFirst().orElse(null);
        Customer customer = customerRepository.findAll().stream()
            .filter(c -> c.getEmail().equals(dto.customer().email()))
            .findFirst().orElse(null);
        if (mobile == null) {
            throw new IllegalArgumentException("Mobile not found");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        if (mobile.getStockQuantity() < dto.quantity()) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        mobile.setStockQuantity(mobile.getStockQuantity() - dto.quantity());
        mobileRepository.save(mobile);
        Purchase purchase = new Purchase(
            mobile,
            customer,
            java.time.LocalDateTime.now(),
            dto.quantity(),
            mobile.getPrice().multiply(java.math.BigDecimal.valueOf(dto.quantity()))
        );
        return purchaseRepository.save(purchase);
    }

    @Override
    public Purchase getPurchase(UUID id) {
        return purchaseRepository.findById(id).orElse(null);
    }
}
