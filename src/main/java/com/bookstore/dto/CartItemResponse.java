package com.bookstore.dto;

import com.bookstore.model.CartItem;

public class CartItemResponse {
    private Long id;
    private Long bookId;
    private String title;
    private String author;
    private String category;  // ✅ เพิ่ม field นี้
    private double price;
    private int quantity;

    // ✅ Factory method for mapping
    public static CartItemResponse from(CartItem item) {
        CartItemResponse response = new CartItemResponse();
        response.id = item.getId();
        response.bookId = item.getBook().getId();
        response.title = item.getBook().getTitle();
        response.author = item.getBook().getAuthor();
        response.category = item.getBook().getCategory(); // ✅ ดึง category จาก book
        response.price = item.getBook().getPrice();
        response.quantity = item.getQuantity();
        return response;
    }

    // Getters
    public Long getId() { return id; }
    public Long getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }  // ✅ getter ใหม่
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
