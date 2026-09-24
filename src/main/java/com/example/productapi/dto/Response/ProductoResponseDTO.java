package com.example.productapi.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoResponseDTO(

        Long id,

        String nombre,

        BigDecimal precio,

        Integer cantidad,

        LocalDateTime fechaCreacion,

        LocalDateTime fechadeactualizacion,

        Long categoriaId,

        String categoriaNombre


) {

}
