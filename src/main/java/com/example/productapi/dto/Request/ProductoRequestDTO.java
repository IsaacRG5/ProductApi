package com.example.productapi.dto.Request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductoRequestDTO<CategoriaId>(
        @NotBlank(message = "El nombre no puede estar vacio.")
        String nombre,

        @NotNull(message = "El precio no puede estar vacia.")
        @Positive(message = "El precio de ser mayor a 0.")
        BigDecimal precio,

        @PositiveOrZero(message = "La cantidad debe ser mayor o igual a 0.")
        @NotNull(message = "La cantidad no puede estar vacia.")
        Integer cantidad,

        @NotNull(message = "El ID de la categoría es obligatorio           ")
        Long categoriaId
) {
}
