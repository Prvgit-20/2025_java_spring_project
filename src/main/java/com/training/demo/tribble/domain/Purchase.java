package com.training.demo.tribble.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne(optional = false)
    @JoinColumn(name = "mobile_id")
    private Mobile mobile;
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @Column(nullable = false)
    private LocalDateTime purchasedAt;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private BigDecimal totalPrice;

    public Purchase() {}
    public Purchase(Mobile mobile, Customer customer, LocalDateTime purchasedAt, int quantity, BigDecimal totalPrice) {
        this.id = UUID.randomUUID();
        this.mobile = mobile;
        this.customer = customer;
        this.purchasedAt = purchasedAt;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Mobile getMobile() { return mobile; }
    public void setMobile(Mobile mobile) { this.mobile = mobile; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public LocalDateTime getPurchasedAt() { return purchasedAt; }
    public void setPurchasedAt(LocalDateTime purchasedAt) { this.purchasedAt = purchasedAt; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}
