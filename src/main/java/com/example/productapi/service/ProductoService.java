package com.example.productapi.service;

import com.example.productapi.dto.ProductoMapper;
import com.example.productapi.dto.Request.ProductoRequestDTO;
import com.example.productapi.dto.Response.ProductoResponseDTO;
import com.example.productapi.model.Categoria;
import com.example.productapi.model.Producto;
import com.example.productapi.repository.CategoriaRepository;
import com.example.productapi.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    public ProductoResponseDTO save(ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con el ID" + dto.categoriaId()));

        Producto producto = productoMapper.toEntity(dto);
        producto.setCategoria(categoria);

        Producto savedProduct = productoRepository.save(producto);
        return productoMapper.toResponseDTO(savedProduct);
    }

    public ProductoResponseDTO update(Long id, ProductoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con el ID" + dto.categoriaId()));

        return productoRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setNombre(dto.nombre());
                    existingProduct.setPrecio(dto.precio());
                    existingProduct.setCantidad(dto.cantidad());
                    existingProduct.setCategoria(categoria);
                    existingProduct.setFechadeactualizacion(LocalDateTime.now());
                    Producto updated = productoRepository.save(existingProduct);
                    return productoMapper.toResponseDTO(updated);
                })
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con el ID: " + id));
    }

    public void remove(Long id) {
        Producto producto = productoRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));
        productoRepository.delete(producto);

    }


    public ProductoResponseDTO findById(Long id) {
        return productoMapper.toResponseDTO(findEntityById(id));
    }

    private Producto findEntityById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));
    }

    public List<ProductoResponseDTO> findByCategoryName(String categoryName) {
        return productoRepository.findByCategoryNameContainingIgnoreCase(categoryName)
                .stream()
                .map(productoMapper::toResponseDTO)
                .toList();
    }

    public List<ProductoResponseDTO> findAll() {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toResponseDTO)
                .toList();
    }

    public List<ProductoResponseDTO> findByCategoryId(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con el ID: " + id);
        }
        return productoRepository.findByCategoryId(id)
                .stream()
                .map(productoMapper::toResponseDTO)
                .toList();
    }
}
