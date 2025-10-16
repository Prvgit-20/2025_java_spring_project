package com.training.demo.tribble.dto;

public record PurchaseRequestDto(MobileDto mobile, CustomerDto customer, int quantity) {}

