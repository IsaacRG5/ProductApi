package com.example.productapi.repository;

import com.example.productapi.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoryId(Long categoriaId);

    List<Producto> findByCategoryNameContainingIgnoreCase(String categoriaNombre);

}
