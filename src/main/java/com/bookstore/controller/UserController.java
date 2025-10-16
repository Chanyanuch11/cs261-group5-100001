package com.bookstore.controller;

import com.bookstore.model.User;
import com.bookstore.service.UserService;
import com.bookstore.dto.LoginResponse;
import com.bookstore.dto.SignupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody User user) {
        User savedUser = userService.signup(user);

        SignupResponse response = new SignupResponse(
                "Signup successful!",
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    // Login endpoint (now includes user ID)
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (user.isPresent()) {
            User u = user.get();
            LoginResponse response = new LoginResponse(
                    u.getId(),                        // ✅ include user ID
                    "Login successful!",
                    u.getUsername(),
                    u.getEmail()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401)
                    .body(new LoginResponse(null, "Invalid email or password", null, null));
        }
    }
}
