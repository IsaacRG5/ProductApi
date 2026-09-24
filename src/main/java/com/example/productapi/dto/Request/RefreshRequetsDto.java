package com.example.productapi.dto.Request;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequetsDto(
        @NotBlank(message = "El refreshToken es obligatorio")
        String refreshToken
) {}
