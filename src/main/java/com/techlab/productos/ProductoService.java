package com.techlab.productos;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techlab.excepciones.ProductoDuplicadoException;
import com.techlab.excepciones.ProductoNoEncontradoException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Transactional
    public Producto crear(String nombre, BigDecimal precio, int stock) {
        Producto producto = new Producto(nombre, precio, stock);
        if (productoRepository.existsByNombre(producto.getNombre())) {
            throw new ProductoDuplicadoException(producto.getNombre());
        }
        return productoRepository.save(producto);
    }

    public List<Producto> listar() {
        return productoRepository.findAll(Sort.by("nombre"));
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    public List<Producto> buscarPorNombre(String texto) {
        return productoRepository.findByNombreContainingIgnoreCase(texto);
    }

    @Transactional
    public Producto actualizarPrecio(Long id, BigDecimal nuevoPrecio) {
        Producto producto = buscarPorId(id);
        producto.setPrecio(nuevoPrecio);
        return producto;
    }

    @Transactional
    public Producto actualizarStock(Long id, int nuevoStock) {
        Producto producto = buscarPorId(id);
        producto.setStock(nuevoStock);
        return producto;
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = buscarPorId(id);
        productoRepository.delete(producto);
    }
}
