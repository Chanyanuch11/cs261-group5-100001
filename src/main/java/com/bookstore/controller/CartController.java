package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import com.bookstore.repo.BookRepository;
import com.bookstore.repo.CartRepository;
import com.bookstore.dto.CartItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final BookRepository bookRepository;

    public CartController(CartRepository cartRepository, BookRepository bookRepository) {
        this.cartRepository = cartRepository;
        this.bookRepository = bookRepository;
    }

    // ✅ Add to cart (update quantity if exists)
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartItem cartItem) {
        Optional<Book> bookOpt = bookRepository.findById(cartItem.getBook().getId());
        if (bookOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Book not found");
        }

        Book book = bookOpt.get();

        Optional<CartItem> existingItem = cartRepository.findByUserIdAndBook_Id(
                cartItem.getUserId(), book.getId()
        );

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
            cartRepository.save(item);
        } else {
            cartItem.setBook(book); // ✅ Ensure book object is linked
            cartRepository.save(cartItem);
        }

        return ResponseEntity.ok("✅ Added to cart");
    }

    // ✅ Get all cart items (title now shows correctly)
    @GetMapping("/{userId}")
    public List<CartItemResponse> getCart(@PathVariable Long userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        return items.stream()
                .map(CartItemResponse::from) // ✅ convert entity → DTO
                .toList();
    }


    // ✅ Remove item from cart
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
            return ResponseEntity.ok("🗑️ Removed from cart");
        }
        return ResponseEntity.badRequest().body("❌ Cart item not found");
    }
}
