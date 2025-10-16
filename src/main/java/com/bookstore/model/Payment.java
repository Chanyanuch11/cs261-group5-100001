package com.bookstore.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "payments")
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String bankName;
    private String accountNumber;
    private double amount;

    private LocalDate transferDate; // yyyy-MM-dd
    private LocalTime transferTime; // HH:mm

    private String slipFileName;    // ชื่อไฟล์สลิปถ้าอัปโหลด
    private String status = "RECEIVED"; // หรือ WAITING_VERIFY

    // getters/setters...
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public LocalDate getTransferDate() { return transferDate; }
    public void setTransferDate(LocalDate transferDate) { this.transferDate = transferDate; }
    public LocalTime getTransferTime() { return transferTime; }
    public void setTransferTime(LocalTime transferTime) { this.transferTime = transferTime; }
    public String getSlipFileName() { return slipFileName; }
    public void setSlipFileName(String slipFileName) { this.slipFileName = slipFileName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}