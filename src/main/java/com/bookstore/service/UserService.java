package com.bookstore.service;

import com.bookstore.model.User;
import com.bookstore.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Save user during signup
    public User signup(User user) {
        return userRepository.save(user);
    }

    // ✅ Login: check email first, then compare password manually
    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            if (user.get().getPassword().equals(password)) {
                return user; // success
            }
        }

        return Optional.empty(); // fail
    }
}
