package com.bookstore.dto;

import com.bookstore.model.Order;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Long userId;
    private Long shippingAddressId;
    private String orderDate;

    private double subTotal;
    private double shippingFee;
    private double discount;

    private double total;
    private String status;
    private List<OrderItemResponse> items;

    public static OrderResponse from(Order order) {
        OrderResponse res = new OrderResponse();
        res.id = order.getId();
        res.userId = order.getUserId();
        res.shippingAddressId = order.getShippingAddressId();
        res.orderDate = order.getOrderDate().toString();
        res.subTotal = order.getSubTotal();
        res.shippingFee = order.getShippingFee();
        res.discount = order.getDiscount();
        res.total = order.getTotal();
        res.status = order.getStatus(); // ✅ เพิ่มบรรทัดนี้
        res.items = order.getItems().stream()
                .map(OrderItemResponse::from)
                .toList();
        return res;
    }

    // ✅ Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getShippingAddressId() { return shippingAddressId; } // ✅ เพิ่ม
    public String getOrderDate() { return orderDate; }
    public double getTotal() { return total; }
    public double getSubTotal() { return subTotal; }
    public double getShippingFee() { return shippingFee; }
    public double getDiscount() { return discount; }
    public String getStatus() { return status; } // ✅ เพิ่ม
    public List<OrderItemResponse> getItems() { return items; }
}