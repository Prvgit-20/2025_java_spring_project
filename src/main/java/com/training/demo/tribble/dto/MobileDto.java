package com.training.demo.tribble.dto;

import java.math.BigDecimal;

public record MobileDto(
    String brand,
    String model,
    String imei,
    BigDecimal price,
    String color,
    int stockQuantity
) {}
