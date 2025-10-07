package com.bookstore.dto;

import com.bookstore.model.OrderItem;

public class OrderItemResponse {
    private String title;
    private int quantity;
    private double price;

    public static OrderItemResponse from(OrderItem item) {
        OrderItemResponse res = new OrderItemResponse();
        res.title = item.getBook().getTitle();
        res.quantity = item.getQuantity();
        res.price = item.getPrice();
        return res;
    }

    // --- Getters ---
    public String getTitle() { return title; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}
