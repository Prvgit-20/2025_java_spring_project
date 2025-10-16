package com.training.demo.tribble.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "mobiles")
public class Mobile {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, unique = true)
    private String imei;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private int stockQuantity;

    public Mobile() {}

    public Mobile(String brand, String model, String imei, BigDecimal price, String color, int stockQuantity) {
        this.id = UUID.randomUUID();
        this.brand = brand;
        this.model = model;
        this.imei = imei;
        this.price = price;
        this.color = color;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getImei() { return imei; }
    public void setImei(String imei) { this.imei = imei; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}
