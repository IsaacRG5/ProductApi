package com.example.productapi.repository;

import com.example.productapi.model.RefreshToken;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRespository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    java.util.Optional<Object> findById(String token);
}
