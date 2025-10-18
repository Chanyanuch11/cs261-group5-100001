package com.bookstore.service;

import com.bookstore.model.*;
import com.bookstore.repo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;

    public OrderService(OrderRepository orderRepo, CartRepository cartRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
    }

    @Transactional
    public Order confirmOrder(Long userId, double shippingFee, double discount) {
        List<CartItem> cartItems = cartRepo.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        double subTotal = cartItems.stream()
                .mapToDouble(ci -> ci.getBook().getPrice() * ci.getQuantity())
                .sum();

        double total = subTotal + shippingFee - discount;

        Order order = new Order();
        order.setUserId(userId);
        order.setSubTotal(subTotal);
        order.setShippingFee(shippingFee);
        order.setDiscount(discount);
        order.setTotal(total);
        order.setStatus("PENDING_PAYMENT");

        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setBook(ci.getBook());
            oi.setQuantity(ci.getQuantity());
            order.getItems().add(oi);
        }

        Order saved = orderRepo.save(order);
        cartRepo.deleteByUserId(userId); // เคลียร์ตะกร้า
        return saved;
    }
}