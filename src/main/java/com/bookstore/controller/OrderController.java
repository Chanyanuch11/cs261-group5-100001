package com.bookstore.controller;

import com.bookstore.dto.OrderResponse;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import com.bookstore.repo.CartRepository;
import com.bookstore.repo.OrderRepository;
import com.bookstore.repo.ShippingAddressRepository; // ✅ เพิ่ม import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ShippingAddressRepository shippingAddressRepository; // ✅ เพิ่ม field

    // ✅ Constructor ใหม่ (เพิ่ม shippingAddressRepository)
    public OrderController(OrderRepository orderRepository,
                           CartRepository cartRepository,
                           ShippingAddressRepository shippingAddressRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.shippingAddressRepository = shippingAddressRepository;
    }

    // ✅ Step 2: Confirm/Checkout — สร้างออเดอร์จากตะกร้า + สรุปยอด
    @PostMapping("/checkout/{userId}")
    public ResponseEntity<?> checkout(
            @PathVariable Long userId,
            @RequestParam Long addressId,
            @RequestParam(defaultValue = "0") double shippingFee,
            @RequestParam(defaultValue = "0") double discount) {

        List<CartItem> cartItems = cartRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Cart is empty");
        }

        // ✅ ตรวจสอบว่า addressId เป็นของ userId เดียวกัน
        var addr = shippingAddressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("❌ Address not found"));
        if (!addr.getUser().getId().equals(userId)) {
            return ResponseEntity.badRequest().body("❌ Address not yours");
        }

        // ✅ สร้าง Order
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddressId(addressId);

        double subTotal = 0.0;
        for (CartItem cart : cartItems) {
            OrderItem item = new OrderItem();
            item.setBook(cart.getBook());
            item.setQuantity(cart.getQuantity());
            item.setPrice(cart.getBook().getPrice());
            item.setOrder(order);

            subTotal += cart.getBook().getPrice() * cart.getQuantity();
            order.getItems().add(item);
        }

        order.setSubTotal(subTotal);
        order.setShippingFee(shippingFee);
        order.setDiscount(discount);
        order.setTotal(subTotal + shippingFee - discount);
        order.setStatus("PENDING_PAYMENT");

        Order saved = orderRepository.save(order);

        // ✅ clear cart after checkout
        cartRepository.deleteAll(cartItems);

        // ✅ ส่งรายละเอียดกลับให้หน้า Step 2 แสดงยอด
        return ResponseEntity.ok(OrderResponse.from(saved));
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