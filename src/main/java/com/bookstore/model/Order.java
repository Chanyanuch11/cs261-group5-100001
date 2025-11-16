package com.bookstore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private LocalDateTime orderDate = LocalDateTime.now();

    // ✅ เพิ่มฟิลด์นี้
    private Long shippingAddressId;

    private double subTotal;
    private double shippingFee;
    private double discount;
    private double total;

    private String status = "PENDING_PAYMENT";

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    // ✅ getter/setter ใหม่
    public Long getShippingAddressId() { return shippingAddressId; }
    public void setShippingAddressId(Long shippingAddressId) { this.shippingAddressId = shippingAddressId; }

    public double getSubTotal() { return subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }
    public double getShippingFee() { return shippingFee; }
    public void setShippingFee(double shippingFee) { this.shippingFee = shippingFee; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}