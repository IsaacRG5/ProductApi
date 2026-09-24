package com.example.productapi.dto;

import com.example.productapi.dto.Request.ProductoRequestDTO;
import com.example.productapi.dto.Response.ProductoResponseDTO;
import com.example.productapi.model.Producto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductoMapper {
    // Entity -> ResponseDTO (para devolver al cliente)
    public ProductoResponseDTO toResponseDTO(Producto producto) {


        Long categoryId = producto.getCategoria() != null ? producto.getCategoria().getId() : null;
        String categoryNombre = producto.getCategoria() != null ? producto.getCategoria().getNombre() : null;

        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCantidad(),
                producto.getFechaCreacion(),
                producto.getFechadeactualizacion(),
                categoryId,
                categoryNombre



        );
    }

    // RequestDTO -> Entity (para crear un nuevo producto)
    public Producto toEntity(ProductoRequestDTO dto) {
        if (dto == null) return null;

        return Producto.builder()
                .nombre(dto.nombre())
                .precio(dto.precio())
                .cantidad(dto.cantidad())
                .fechaCreacion(LocalDateTime.now())
                .fechadeactualizacion(LocalDateTime.now())
                .build();
    }

    // RequestDTO -> actualiza una Entity existente (para PUT/PATCH)
    public void updateEntityFromDTO(ProductoRequestDTO dto, Producto producto) {
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setCantidad(dto.cantidad());
        producto.setFechadeactualizacion(LocalDateTime.now());
    }

}
