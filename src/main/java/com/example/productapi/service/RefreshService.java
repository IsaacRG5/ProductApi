package com.example.productapi.service;


import com.example.productapi.model.RefreshToken;
import com.example.productapi.repository.RefreshTokenRespository;
import com.example.productapi.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshService {

    private final RefreshTokenRespository refreshTokenRespository;

    @Value("${security.jwt.refresh-token-expiration-ms}")
    private long refreshTokenExpirationMs;

    public RefreshToken create (String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .username(username)
                .expiresAt(Instant.now().plusMillis(refreshTokenExpirationMs))
                .revoked(false)
                .build();

        return refreshTokenRespository.save(refreshToken);
    }

    public RefreshToken validate(String token) throws InvalidRefreshTokenException {
        RefreshToken refreshToken = (RefreshToken) refreshTokenRespository.findById(token)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token inválido"));

        if (refreshToken.isRevoked() || refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Refresh token expirado o revocado");
        }

        return refreshToken;
    }

    public void revoke(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        refreshTokenRespository.save(refreshToken);
    }
}
