package com.bookstore.dto;

import java.util.List;

public class OrderFinalResponse {
    // Order info
    public Long id;
    public Long userId;
    public String orderDate;
    public double subTotal;
    public double shippingFee;
    public double discount;
    public double total;
    public String status;

    // Relation info
    public ShippingAddressResponse shippingAddress;
    public List<OrderItemResponse> items;

    // Payment info
    public PaymentInfoResponse payment;

    public OrderFinalResponse() {}
}