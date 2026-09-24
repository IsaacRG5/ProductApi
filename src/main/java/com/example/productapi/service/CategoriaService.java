package com.example.productapi.service;

import com.example.productapi.dto.CategoriaMapper;
import com.example.productapi.dto.Request.CategoriaRequestDTO;
import com.example.productapi.dto.Response.CategoriaResponseDTO;
import com.example.productapi.model.Categoria;
import com.example.productapi.model.Producto;
import com.example.productapi.repository.CategoriaRepository;
import com.example.productapi.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final CategoriaMapper categoriaMapper;

    public List<CategoriaResponseDTO> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .toList();
    }

    public CategoriaResponseDTO findById(Long id) {
        return categoriaMapper.toResponseDTO(findEntityById(id));
    }



    public List<CategoriaResponseDTO> findByCategorianombre(String categorianombre) {
        return categoriaRepository.findByNameContainingIgnoreCase(categorianombre)
                .stream()
                .map(categoriaMapper :: toResponseDTO)
                .toList();
    }

    public CategoriaResponseDTO save (CategoriaRequestDTO dto) {
        Categoria categoria = categoriaMapper.toEntity(dto);
        Categoria savedcategoria = categoriaRepository.save(categoria);
        return categoriaMapper.toResponseDTO(savedcategoria);
    }

    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = findEntityById(id); // lanza ResourceNotFoundException si no existe

        categoria.setNombre(dto.nombre());
        categoria.setDescripcion(dto.descripcion());

        Categoria updated = categoriaRepository.save(categoria);
        return categoriaMapper.toResponseDTO(updated);
    }

    @Transactional // Garantiza que si algo falla, se revierten los cambios
    public void remove(Long id) {

        Categoria categoria = findEntityById(id);


        List<Producto> products = productoRepository.findByCategoryId(id);


        for (Producto producto : products) {
            producto.setCategoria(null);
            productoRepository.save(producto);
        }

        // Borra la categoría de forma segura
        categoriaRepository.delete(categoria);
    }


    private Categoria findEntityById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + id));
    }
}


