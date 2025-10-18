package com.bookstore.controller;

import com.bookstore.model.User;
import com.bookstore.service.UserService;
import com.bookstore.dto.LoginResponse;
import com.bookstore.dto.SignupResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000") // 👈 change port if your frontend uses another
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Signup endpoint with validation
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody User user) {
        User savedUser = userService.signup(user);

        SignupResponse response = new SignupResponse(
                "Signup successful!",
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.login(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        if (user.isPresent()) {
            User u = user.get();
            LoginResponse response = new LoginResponse(
                    u.getId(),                        // include user ID
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

    // ⚙️ Optional: Friendly error message for validation issues
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
