package com.bookstore.dto;

public class SignupResponse {
    private String message;
    private String username;
    private String email;

    public SignupResponse(String message, String username, String email) {
        this.message = message;
        this.username = username;
        this.email = email;
    }

    // ✅ Getters
    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}