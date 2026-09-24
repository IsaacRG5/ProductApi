package com.example.productapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "nombre" , nullable = false)
    private String nombre;

    @Column (name = "precio" , nullable = false, precision = 19, scale = 2)
    private BigDecimal precio;

    @Column (name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column (name = "stock", nullable = false)
    private Integer stock;

    @CreatedDate
    @Column (name = "fecha_de_creacion" , updatable = false)
    private LocalDateTime fechaCreacion;

    @LastModifiedBy
    @Column(name = "fecha_de_actualizacion")
    private LocalDateTime fechadeactualizacion ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

}
