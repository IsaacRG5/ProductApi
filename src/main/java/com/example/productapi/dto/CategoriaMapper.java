package com.example.productapi.dto;

import com.example.productapi.dto.Request.CategoriaRequestDTO;
import com.example.productapi.dto.Response.CategoriaResponseDTO;
import com.example.productapi.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    // Entity -> ResponseDTO (para devolver al cliente)
    public CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    // RequestDTO -> Entity (para crear una nueva categoría)
    public static Categoria toEntity(CategoriaRequestDTO dto) {
        return Categoria.builder()
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .build();
    }

    // RequestDTO -> actualiza una Entity existente (para PUT/PATCH)
    public static void updateEntityFromDTO(CategoriaRequestDTO dto, Categoria existente) {
        existente.setNombre(dto.nombre());
        existente.setDescripcion(dto.descripcion());
    }


}
