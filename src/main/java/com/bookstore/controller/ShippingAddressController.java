package com.bookstore.controller;

import com.bookstore.dto.ShippingAddressRequest;
import com.bookstore.dto.ShippingAddressResponse;
import com.bookstore.model.ShippingAddress;
import com.bookstore.service.ShippingAddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class ShippingAddressController {

    private final ShippingAddressService service;

    public ShippingAddressController(ShippingAddressService service) {
        this.service = service;
    }

    // สร้างที่อยู่ใหม่
    @PostMapping
    public ResponseEntity<ShippingAddressResponse> create(@Valid @RequestBody ShippingAddressRequest req) {
        ShippingAddress saved = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/addresses/" + saved.getId()))
                .body(ShippingAddressResponse.from(saved));
    }

    // ดูที่อยู่ตาม id
    @GetMapping("/{id}")
    public ResponseEntity<ShippingAddressResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(ShippingAddressResponse.from(service.get(id)));
    }

    // ดูรายการที่อยู่ทั้งหมดของ user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ShippingAddressResponse>> list(@PathVariable Long userId) {
        List<ShippingAddressResponse> res = service.listByUser(userId)
                .stream().map(ShippingAddressResponse::from).toList();
        return ResponseEntity.ok(res);
    }

    // อัปเดตที่อยู่
    @PutMapping("/{id}")
    public ResponseEntity<ShippingAddressResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ShippingAddressRequest req) {
        return ResponseEntity.ok(ShippingAddressResponse.from(service.update(id, req)));
    }

    // ลบที่อยู่ (ต้องส่ง userId มายืนยันความเป็นเจ้าของ)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @RequestParam Long userId) {
        service.delete(id, userId);
        return ResponseEntity.noContent().build();
    }
}