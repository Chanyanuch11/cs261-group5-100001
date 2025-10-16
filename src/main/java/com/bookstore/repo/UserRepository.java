package com.bookstore.repo;

import com.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // ✅ Only keep this
    Optional<User> findByEmail(String email);
}