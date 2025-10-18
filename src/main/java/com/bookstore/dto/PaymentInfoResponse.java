package com.bookstore.dto;

public class PaymentInfoResponse {
    public Long id;
    public double amount;
    public String bankName;
    public String accountNumber;
    public String status;
    public String transferDate;
    public String transferTime;
    public String slipUrl;

    public PaymentInfoResponse(Long id, double amount, String bankName, String accountNumber,
                               String status, String transferDate, String transferTime, String slipUrl) {
        this.id = id;
        this.amount = amount;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.status = status;
        this.transferDate = transferDate;
        this.transferTime = transferTime;
        this.slipUrl = slipUrl;
    }
}