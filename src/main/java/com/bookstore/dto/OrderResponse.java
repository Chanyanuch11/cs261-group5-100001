package com.bookstore.dto;

import com.bookstore.model.Order;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Long userId;
    private String orderDate;
    private double total;
    private List<OrderItemResponse> items;

    // ✅ Factory method to convert entity -> DTO
    public static OrderResponse from(Order order) {
        OrderResponse res = new OrderResponse();
        res.id = order.getId();
        res.userId = order.getUserId();
        res.orderDate = order.getOrderDate().toString();
        res.total = order.getTotal();
        res.items = order.getItems().stream()
                .map(OrderItemResponse::from)
                .toList();
        return res;
    }

    // --- Getters ---
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getOrderDate() { return orderDate; }
    public double getTotal() { return total; }
    public List<OrderItemResponse> getItems() { return items; }
}
