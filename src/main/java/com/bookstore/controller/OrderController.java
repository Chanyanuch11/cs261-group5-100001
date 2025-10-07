package com.bookstore.controller;

import com.bookstore.dto.OrderResponse;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import com.bookstore.repo.CartRepository;
import com.bookstore.repo.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    public OrderController(OrderRepository orderRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
    }

    // ✅ Checkout: create order from cart items
    @PostMapping("/checkout/{userId}")
    public ResponseEntity<?> checkout(@PathVariable Long userId) {
        List<CartItem> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Cart is empty");
        }

        Order order = new Order();
        order.setUserId(userId); // ✅ use the path variable userId
        order.setOrderDate(LocalDateTime.now());

        double total = 0.0;

        for (CartItem cart : cartItems) {
            OrderItem item = new OrderItem();
            item.setBook(cart.getBook());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getBook().getPrice());
            item.setOrder(order);

            total += cart.getBook().getPrice() * cart.getQuantity();
            order.getItems().add(item);
        }

        order.setTotal(total);
        orderRepository.save(order);

        // ✅ clear cart after checkout
        cartRepository.deleteAll(cartItems);

        return ResponseEntity.ok("✅ Order placed successfully!");
    }


    // ✅ Get all orders for a specific user
    @GetMapping("/{userId}")
    public List<OrderResponse> getOrders(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderResponse::from)
                .toList();
    }
}
