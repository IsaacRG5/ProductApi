package com.example.productapi.controller;


import com.example.productapi.dto.Request.ProductoRequestDTO;
import com.example.productapi.dto.Response.ProductoResponseDTO;
import com.example.productapi.service.ProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/Productos")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints for managing products"
)
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAll() {
        return ResponseEntity.ok(productoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }


    @GetMapping("/categoria/{categoriaId}")
    public  ResponseEntity<List<ProductoResponseDTO>> findByCategoria(@PathVariable Long categoriaId) {
        return  ResponseEntity.ok(productoService.findByCategoryId(categoriaId));
    }

    @GetMapping("/buscar-categoria")
    public ResponseEntity<List<ProductoResponseDTO>> findByCategoria(
            @RequestParam (name = "nombre") String categoriaNombre
    ) {
        return ResponseEntity.ok(productoService.findByCategoryName(categoriaNombre));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> save(@RequestBody ProductoRequestDTO productoRequestDTO) {
        return ResponseEntity.ok(productoService.save(productoRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(

            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto) {

        return ResponseEntity.ok(productoService.update(id, dto));

    }
    @DeleteMapping("/{id}")
             public ResponseEntity<Void> remove(
                @PathVariable Long id) {
        productoService.remove(id);
        return ResponseEntity.noContent().build();
    }


}
