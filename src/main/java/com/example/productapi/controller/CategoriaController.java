package com.example.productapi.controller;

import com.example.productapi.dto.Request.CategoriaRequestDTO;
import com.example.productapi.dto.Response.CategoriaResponseDTO;
import com.example.productapi.service.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Categories", description = "Endpoints for managing categories")
@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> findAll() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CategoriaResponseDTO>> findBynombre(
            @RequestParam(name = "nombre") String categorianombre) {

        return ResponseEntity.ok(categoriaService.findByCategorianombre(categorianombre));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> save(@Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO cratedcategoria = categoriaService.save(dto);

        URI location = URI.create("/api/categorias" + cratedcategoria.id());
        return ResponseEntity.created(location).body(cratedcategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO updatedcategoria = categoriaService.update(id, dto);
        return ResponseEntity.ok(updatedcategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
