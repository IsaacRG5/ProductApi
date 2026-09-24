package com.example.productapi.dto.Request;
import jakarta.validation.constraints.*;

public record CategoriaRequestDTO(
        @NotBlank(message = "El nombre no puede estar vacio")
        String nombre,

        @NotBlank(message = "La descripción no puede estar vacia")
        String descripcion
) {
}
