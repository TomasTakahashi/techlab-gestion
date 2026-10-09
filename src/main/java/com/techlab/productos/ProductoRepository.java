package com.techlab.productos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByNombre(String nombre);

    List<Producto> findByNombreContainingIgnoreCase(String texto);
}
